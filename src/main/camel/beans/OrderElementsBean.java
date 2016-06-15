package main.camel.beans;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class OrderElementsBean {

    private static final Logger LOGGER = Logger.getLogger("FILE");


    @Handler
    public String process(@Body String order, Exchange exchange)
    {
        //logging at the beginning of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t|\t Order Nr.: " + exchange.getIn().getHeader("orderID"));

        return "New ELEMENTS WERE ORDERED - OK!";
    }
}
