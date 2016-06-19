package main.camel.beans;

import main.model.CarOrder;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OrderElementsBean {

    private static final Logger LOGGER = Logger.getLogger("FILE");

    public void countDelay(Exchange exchange){

        exchange.setOut(exchange.getIn());

        Random rnd = new Random(System.currentTimeMillis());
        int number = 1 + rnd.nextInt(10);
        exchange.getOut().setHeader("delay", number);
    }

    public void makeOrder(@Body String body, Exchange exchange)
    {
        CarOrder order = exchange.getIn().getBody(CarOrder.class);
        exchange.getOut().setBody("We need details, more details!");
        exchange.getOut().setHeader(exchange.FILE_NAME, "orderedfor" +order.getModel().toString() + ".txt");
    }

 }
