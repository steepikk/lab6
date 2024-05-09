package network.requests;

import utility.Commands;

public class ClearRequest extends Request {
    public ClearRequest() {
        super(Commands.CLEAR);
    }
}
