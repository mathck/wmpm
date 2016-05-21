package main.camel.beans;

/**
 * Created by Mnishko Sergei on 20.05.2016.
 */
public class finalizeOrder {
    public String finalizeOrder (String body)
    {
        return body + " complete";
    }
}
