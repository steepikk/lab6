package network.requests;

import data.Dragon;
import utility.Commands;

public class AddIfMaxRequest extends Request {
    public final Dragon dragon;

    public AddIfMaxRequest(Dragon dragon) {
        super(Commands.ADD_IF_MAX);
        this.dragon = dragon;
    }
}
