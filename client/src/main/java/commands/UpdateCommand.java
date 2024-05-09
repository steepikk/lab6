package commands;

import exceptions.*;
import input.Console;
import input.DragonInput;
import network.UDPClient;
import network.requests.UpdateRequest;
import network.responses.UpdateResponse;

import java.io.IOException;

/**
 * Команда 'update'. Обновляет элемент коллекции.
 *
 * @author steepikk
 */
public class UpdateCommand extends Command {
    private final Console console;
    private final UDPClient client;

    public UpdateCommand(Console console, UDPClient client) {
        super("update ID {element}");
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

            console.println("* Введите данные обновленного дракона:");
            var updatedProduct = (new DragonInput(console)).make();

            var response = (UpdateResponse) client.sendAndReceiveCommand(new UpdateRequest(id, updatedProduct));
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            console.println("Дракон успешно обновлен.");
            return true;

        } catch (WrongAmountOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (InvalidFormException exception) {
            console.printError("Поля дракона не валидны! Дракон не создан!");
        } catch (NumberFormatException exception) {
            console.printError("ID должен быть представлен числом!");
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (APIException e) {
            console.printError(e.getMessage());
        } catch (IncorrectScriptException ignored) {
        }
        return false;
    }
}
