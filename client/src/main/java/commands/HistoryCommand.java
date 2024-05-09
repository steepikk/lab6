package commands;


import input.Console;
import network.UDPClient;
import network.requests.HistoryRequest;
import network.responses.HistoryResponse;

import java.io.IOException;
import java.util.Collections;

/**
 * Команда 'history'. Выводит последние 8 команд.
 *
 * @author steepikk
 */
public class HistoryCommand extends Command {
    private final Console console;
    private final UDPClient client;

    public HistoryCommand(Console console, UDPClient client) {
        super("history");
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
            var response = (HistoryResponse) client.sendAndReceiveCommand(new HistoryRequest());

            Collections.reverse(response.historyMessage);
            for (var command : response.historyMessage) {
                if (command == null) {
                    console.print("");
                } else {
                    console.println(command);
                }
            }

            return true;
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        }
        return false;
    }
}
