package commands;

import managers.CollectionManager;
import network.requests.Request;
import network.responses.InfoResponse;
import network.responses.Response;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 *
 * @author steepikk
 */
public class InfoCommand extends Command {
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        var lastInitTime = collectionManager.getLastInitTime();
        var lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

        var lastSaveTime = collectionManager.getLastSaveTime();
        var lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

        var message = "Сведения о коллекции:\n" +
                " Тип: " + collectionManager.getType() + "\n" +
                " Количество элементов: " + collectionManager.getSize() + "\n" +
                " Дата последнего сохранения: " + lastSaveTimeString + "\n" +
                " Дата последней инициализации: " + lastInitTimeString;
        return new InfoResponse(message, null);
    }
}
