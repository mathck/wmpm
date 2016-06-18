package main.camel.beans;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Accept30PercentBean {

    private static final Logger LOGGER = Logger.getLogger("FILE");


    @Handler
    public void process (@Body String order, Exchange exchange)
    {

        exchange.setOut(exchange.getIn());

        //logging at the beginning of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t|\t Order Nr.: " + exchange.getIn().getHeader("orderID"));

    }

}
