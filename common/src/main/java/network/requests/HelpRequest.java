package network.requests;

import utility.Commands;

public class HelpRequest extends Request {
    public HelpRequest() {
        super(Commands.HELP);
    }
}
