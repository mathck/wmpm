package main.camel.beans;

import main.model.CarOrder;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class TwitterBean {

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Handler
    public String process(Exchange exchange)
    {
        CarOrder order = exchange.getIn().getBody(CarOrder.class);
        Timestamp now = new Timestamp(new java.util.Date().getTime());

        return  "At " + now +
                " the magic happened. We have created the new future car!" +
                " Model: " + order.getModel() + "; " +
                " Color: " + order.getColor() + "; " +
                " Horsepower: " + order.getHorsepower().toString();
    }
}