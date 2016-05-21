package main.camel.beans;

import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

/**
 * Created by Mnishko Sergei on 20.05.2016.
 */
@Component
public class finalizeOrderBean {

    @Handler
    public String finalizeOrder (String body)
    {
        return body + " complete";
    }
}
