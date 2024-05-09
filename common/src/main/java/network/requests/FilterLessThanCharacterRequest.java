package network.requests;

import data.DragonCharacter;
import utility.Commands;

public class FilterLessThanCharacterRequest extends Request {
    public final String dragonCharacter;

    public FilterLessThanCharacterRequest(String dragonCharacter) {
        super(Commands.FILTER_LESS_THAN_CHARACTER);
        this.dragonCharacter = dragonCharacter;
    }
}
