package main.camel.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Accept70PercentBean {

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Handler
    public void process(Exchange exchange) throws Exception {
        //getting information about payment
        //logging at the beginning of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t|\t Order Nr.: " + exchange.getIn().getHeader("orderID"));
        exchange.setOut(exchange.getIn());

        Random random = new Random();
        exchange.getOut().setHeader("is70percentPaid", random.nextBoolean());

        //logging at the end of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t|\t Order Nr.: " + exchange.getOut().getHeader("orderID") + "  \t|\t New Header: is70percentPaid = " + exchange.getOut().getHeader("is70percentPaid").toString());
    }
}