package commands;

import exceptions.*;
import input.Console;
import input.DragonInput;
import network.UDPClient;
import network.requests.AddRequest;
import network.responses.AddResponse;

import java.io.IOException;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 *
 * @author steepikk
 */
public class AddCommand extends Command {
    private final Console console;
    private final UDPClient client;

    public AddCommand(Console console, UDPClient client) {
        super("add {element}");
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
            console.println("* Создание нового дракона!");

            var newDragon = (new DragonInput(console)).make();
            var response = (AddResponse) client.sendAndReceiveCommand(new AddRequest(newDragon));
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            console.println("Новый дракон с id = " + response.newId + " успешно добавлен!");
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
