package network.requests;

import utility.Commands;

public class CountLessThanAgeRequest extends Request{
    public final String age;

    public CountLessThanAgeRequest(String age){
        super(Commands.COUNT_LESS_THAN_AGE);
        this.age = age;
    }
}
