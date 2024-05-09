package commands;

import input.Console;
import network.UDPClient;
import network.requests.InfoRequest;
import network.responses.InfoResponse;

import java.io.IOException;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 *
 * @author steepikk
 */
public class InfoCommand extends Command {
    private final Console console;
    private final UDPClient client;

    public InfoCommand(Console console, UDPClient client) {
        super("info");
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
        if (!arguments[1].isEmpty()) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        try {
            var response = (InfoResponse) client.sendAndReceiveCommand(new InfoRequest());
            console.println(response.infoMessage);
            return true;
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        }
        return false;
    }
}
