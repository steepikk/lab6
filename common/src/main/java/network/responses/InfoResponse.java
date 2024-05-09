package network.responses;

import utility.Commands;

public class InfoResponse extends Response {
    public final String infoMessage;

    public InfoResponse(String infoMessage, String error) {
        super(Commands.INFO, error);
        this.infoMessage = infoMessage;
    }
}
