package commands;

import input.Console;

/**
 * Команда 'execute_script'. Выполнить скрипт из файла.
 *
 * @author steepikk
 */
public class ExecuteScriptCommand extends Command {
    private final Console console;

    public ExecuteScriptCommand(Console console) {
        super("execute_script <file_name>");
        this.console = console;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        if (arguments[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        console.println("Выполнение скрипта '" + arguments[1] + "'...");
        return true;
    }
}