package commands;

import exceptions.*;
import input.Console;
import network.UDPClient;
import network.requests.RemoveRequest;
import network.responses.RemoveResponse;

import java.io.IOException;

/**
 * Команда 'remove'. Удаляет элемент из коллекции.
 *
 * @author steepikk
 */
public class RemoveCommand extends Command {
    private final Console console;
    private final UDPClient client;

    public RemoveCommand(Console console, UDPClient client) {
        super("remove <ID>");
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
            var id = Integer.parseInt(arguments[1]);

            var response = (RemoveResponse) client.sendAndReceiveCommand(new RemoveRequest(id));
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            console.println("Дракон успешно удален.");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (NumberFormatException exception) {
            console.printError("ID должен быть представлен числом!");
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (APIException e) {
            console.printError(e.getMessage());
        }
        return false;
    }
}
