package network.requests;

import data.Dragon;
import utility.Commands;

public class UpdateRequest extends Request {
    public final int id;
    public final Dragon updatedDragon;

    public UpdateRequest(int id, Dragon updatedDragon) {
        super(Commands.UPDATE);
        this.id = id;
        this.updatedDragon = updatedDragon;
    }
}
