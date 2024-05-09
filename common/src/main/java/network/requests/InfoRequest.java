package network.requests;

import utility.Commands;

public class InfoRequest extends Request {
    public InfoRequest() {
        super(Commands.INFO);
    }
}
