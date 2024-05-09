package commands;

import exceptions.APIException;
import exceptions.IncorrectScriptException;
import exceptions.InvalidFormException;
import exceptions.WrongAmountOfElementsException;
import input.Console;
import input.DragonInput;
import network.UDPClient;
import network.requests.RemoveGreaterRequest;
import network.responses.RemoveGreaterResponse;

import java.io.IOException;

/**
 * Команда 'remove_greater'. Удаляет из коллекции всех элементов больше заданного.
 *
 * @author steepikk
 */
public class RemoveGreaterCommand extends Command {
    private final Console console;
    private final UDPClient client;

    public RemoveGreaterCommand(Console console, UDPClient client) {
        super("remove_greater <element>");
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
            console.println("* Создание нового дракона:");

            var newDragon = (new DragonInput(console)).make();
            var response = (RemoveGreaterResponse) client.sendAndReceiveCommand(new RemoveGreaterRequest(newDragon));
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            console.println("Драконы успешно удалены.");
            return true;

        } catch (WrongAmountOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (InvalidFormException exception) {
            console.printError("Поля дракона не валидны! Дракон не создан!");
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (APIException e) {
            console.printError(e.getMessage());
        } catch (IncorrectScriptException e) {
        }
        return false;
    }
}
