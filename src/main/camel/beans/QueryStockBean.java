package main.camel.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class QueryStockBean {
    private static final Logger LOGGER = Logger.getLogger(ProcessOrderBean.class);

    @Handler
    public void process (Exchange exchange)
    {
        //logging at the beginning of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t|\t Order Nr.: " + exchange.getIn().getHeader("orderID"));

        int orderId = (int)exchange.getIn().getHeader("orderID");
        Random random = new Random();
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
        exchange.getOut().setHeader("enoughElements", random.nextBoolean());

        //logging at the end of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t|\t Order Nr.: " + exchange.getOut().getHeader("orderID") + "\t|\t New Header: enoughElements = " + exchange.getOut().getHeader("enoughElements").toString());
    }
}