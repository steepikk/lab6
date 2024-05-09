package commands;

import exceptions.*;
import input.Console;
import network.UDPClient;
import network.requests.CountLessThanAgeRequest;
import network.responses.CountLessThanAgeResponse;

import java.io.IOException;

/**
 * Команда 'count_less_than_age'. Выводит количества элементов, значение поля age которых меньше заданного.
 *
 * @author steepikk
 */
public class CountLessThanAgeCommand extends Command {
    private final Console console;
    private final UDPClient client;

    public CountLessThanAgeCommand(Console console, UDPClient client) {
        super("count_less_than_age <age>");
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

            var age = arguments[1];
            var response = (CountLessThanAgeResponse) client.sendAndReceiveCommand(new CountLessThanAgeRequest(age));

            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            if (response.countDragons == 0) {
                console.println("Драконов, у которых поле age меньше заданной подстроки не обнаружено!");
            } else {
                console.println("Драконы, у которых поле age меньше заданной подстроки равняется: " + response.countDragons + "!");
            }

            return true;
        } catch (WrongAmountOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (NumberFormatException exception) {
            console.printError("Возраст должен быть представлен числом!");
        } catch (APIException e) {
            console.printError(e.getMessage());
        }
        return false;
    }
}
