package main;

import commands.*;
import handlers.CommandHandler;
import managers.CollectionManager;
import managers.CommandManager;
import managers.DumpManager;
import network.UDPDatagramServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utility.Commands;

import java.net.*;

/**
 * Серверная часть приложения.
 *
 * @author steepikk
 */
public class App {
    public static final int PORT = 1821;

    public static Logger logger = LogManager.getLogger("ServerLogger");

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Введите имя загружаемого файла как аргумент командной строки");
            System.exit(1);
        }

        var dumpManager = new DumpManager(args[0]);
        var collectionManager = new CollectionManager(dumpManager);
        collectionManager.validateAll();

        Runtime.getRuntime().addShutdownHook(new Thread(collectionManager::saveCollection));

        var commandManager = new CommandManager() {{
            register(Commands.HELP, new HelpCommand(this));
            register(Commands.INFO, new InfoCommand(collectionManager));
            register(Commands.SHOW, new ShowCommand(collectionManager));
            register(Commands.ADD, new AddCommand(collectionManager));
            register(Commands.UPDATE, new UpdateCommand(collectionManager));
            register(Commands.REMOVE_BY_ID, new RemoveCommand(collectionManager));
            register(Commands.CLEAR, new ClearCommand(collectionManager));
            register(Commands.COUNT_LESS_THAN_AGE, new CountLessThanAgeCommand(collectionManager));
            register(Commands.ADD_IF_MAX, new AddIfMaxCommand(collectionManager));
            register(Commands.FILTER_LESS_THAN_CHARACTER, new FilterLessThanCharacterCommand(collectionManager));
            register(Commands.HISTORY, new HistoryCommand(collectionManager, this));
            register(Commands.REMOVE_GREATER, new RemoveGreaterCommand(collectionManager));
            register(Commands.PRINT_ASCENDING, new PrintAscendingCommand(collectionManager));
        }};


        try {
            var server = new UDPDatagramServer(InetAddress.getLocalHost(), PORT, new CommandHandler(commandManager));
            server.setAfterHook(collectionManager::saveCollection);
            server.run();
        } catch (SocketException e) {
            logger.fatal("Случилась ошибка сокета", e);
        } catch (UnknownHostException e) {
            logger.fatal("Неизвестный хост", e);
        }
    }
}
