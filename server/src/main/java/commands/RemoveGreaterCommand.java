package commands;


import managers.CollectionManager;
import network.requests.RemoveGreaterRequest;
import network.requests.RemoveRequest;
import network.requests.Request;
import network.responses.RemoveGreaterResponse;
import network.responses.RemoveResponse;
import network.responses.Response;

/**
 * Команда 'remove_greater'. Удаляет из коллекции всех элементов больше заданного.
 *
 * @author steepikk
 */
public class RemoveGreaterCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveGreaterCommand(CollectionManager collectionManager) {
        super("remove_greater", "удаляет из коллекции всех элементов больше заданного");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        var req = (RemoveGreaterRequest) request;

        try {
            if (!collectionManager.checkExist(req.dragon.getId())) {
                return new RemoveResponse("Дракона с таким ID в коллекции нет!");
            }

            collectionManager.removeGreater(req.dragon);
            return new RemoveGreaterResponse(null);
        } catch (Exception e) {
            return new RemoveGreaterResponse(e.toString());
        }
    }
}
