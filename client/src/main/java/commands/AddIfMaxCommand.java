package commands;

import exceptions.*;
import input.Console;
import input.DragonInput;
import network.UDPClient;
import network.requests.AddIfMaxRequest;
import network.responses.AddIfMaxResponse;

import java.io.IOException;

/**
 * Команда 'add_if_max'. Добавляет новый элемент в коллекцию, если его возраст превышает максимальный возраст этой коллекции.
 *
 * @author steepikk
 */
public class AddIfMaxCommand extends Command {
    private final Console console;
    private final UDPClient client;

    public AddIfMaxCommand(Console console, UDPClient client) {
        super("add_if_max {element}");
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
            console.println("* Создание нового дракона (add_if_max)!");

            var newDragon = (new DragonInput(console)).make();
            var response = (AddIfMaxResponse) client.sendAndReceiveCommand(new AddIfMaxRequest(newDragon));
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new APIException(response.getError());
            }

            if (!response.isAdded) {
                console.println("Дракон не добавлен, возраст " + newDragon.getAge() + "не максимальный");
                return true;
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
        } catch (IncorrectScriptException ignored) {
        }
        return false;
    }
}