package network.requests;

import utility.Commands;

public class HistoryRequest extends Request {
    public HistoryRequest() {
        super(Commands.HISTORY);
    }
}
