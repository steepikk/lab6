package commands;

import input.Console;
import network.UDPClient;
import network.requests.PrintAscendingRequest;
import network.responses.PrintAscendingResponse;

import java.io.IOException;

/**
 * Команда 'print_ascending'. Выводит коллекцию в порядке возрастания.
 *
 * @author steepikk
 */
public class PrintAscendingCommand extends Command {
    private final Console console;
    private final UDPClient client;

    public PrintAscendingCommand(Console console, UDPClient client) {
        super("print_ascending");
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
            var response = (PrintAscendingResponse) client.sendAndReceiveCommand(new PrintAscendingRequest());

            if (response.filteredDragons.isEmpty()) {
                console.println("Драконов ниже " + response + " не обнаружено!");
                return true;
            }

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
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        }
        return false;
    }
}
