package commands;

import input.Console;

/**
 * Команда 'exit'. Завершает выполнение.
 *
 * @author steepikk
 */
public class ExitCommand extends Command {
    private final Console console;

    public ExitCommand(Console console) {
        super("exit");
        this.console = console;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        console.println("Завершение выполнения...");
        return true;
    }
}