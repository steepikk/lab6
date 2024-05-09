package commands;

import input.Console;
import network.UDPClient;
import exceptions.*;
import network.requests.FilterLessThanCharacterRequest;
import network.responses.FilterLessThanCharacterResponse;

import java.io.IOException;

/**
 * Команда 'filter_less_than_character'. Выводит элементы, значение поля character которых меньше заданного.
 *
 * @author steepikk
 */
public class FilterLessThanCharacterCommand extends Command {
    private final Console console;
    private final UDPClient client;

    public FilterLessThanCharacterCommand(Console console, UDPClient client) {
        super("filter_less_than_character <character>");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        try {
            if (arguments[1].isEmpty()) throw new WrongAmountOfElementsException();

            var dragonCharacter = arguments[1];
            var response = (FilterLessThanCharacterResponse) client.sendAndReceiveCommand(new FilterLessThanCharacterRequest(dragonCharacter));
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            if (response.filteredDragons.isEmpty()) {
                console.println("Драконов ниже " + dragonCharacter + " не обнаружено!");
                return true;
            }

            console.println("Драконов ниже " + dragonCharacter + ": " + response.filteredDragons.size() + " шт.\n");

            int i = 0;
            for (var dragon : response.filteredDragons) {
                if (i == 0) {
                    console.println(dragon);
                } else {
                    console.println("\n" + dragon);
                }
                i = i + 1;
            }

            return true;
        } catch (WrongAmountOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (APIException e) {
            console.printError(e.getMessage());
        }
        return false;
    }
}
