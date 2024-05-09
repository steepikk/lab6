package commands;

import exceptions.*;
import input.Console;
import network.UDPClient;
import network.requests.ShowRequest;
import network.responses.ShowResponse;

import java.io.IOException;

/**
 * Команда 'show'. Выводит все элементы коллекции.
 *
 * @author steepikk
 */
public class ShowCommand extends Command {
    private final Console console;
    private final UDPClient client;

    public ShowCommand(Console console, UDPClient client) {
        super("show");
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
            if (!arguments[1].isEmpty()) throw new WrongAmountOfElementsException();

            var response = (ShowResponse) client.sendAndReceiveCommand(new ShowRequest());
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            if (response.dragons.isEmpty()) {
                console.println("Коллекция пуста!");
                return true;
            }

            int i = 0;
            for (var dragon : response.dragons) {
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
