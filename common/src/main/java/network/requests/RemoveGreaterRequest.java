package network.requests;

import data.Dragon;
import utility.Commands;

public class RemoveGreaterRequest extends Request {
    public final Dragon dragon;

    public RemoveGreaterRequest(Dragon dragon) {
        super(Commands.REMOVE_GREATER);
        this.dragon = dragon;
    }
}
