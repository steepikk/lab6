package network.requests;

import data.Dragon;
import utility.Commands;

public class AddRequest extends Request {
    public final Dragon dragon;

    public AddRequest(Dragon dragon) {
        super(Commands.ADD);
        this.dragon = dragon;
    }
}
