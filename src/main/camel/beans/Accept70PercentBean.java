package main.camel.beans;

import main.model.CarOrder;
import main.model.enums.OrderStatus;
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
        Random random = new Random();
        Boolean paymentSuccessful = random.nextBoolean();
        exchange.getIn().setHeader("is70percentPaid", paymentSuccessful);

        if (paymentSuccessful) {
            exchange.getIn().getBody(CarOrder.class).setStatus(OrderStatus.PAYMENTCOMPLETED);
        }

        //exchange.setOut(exchange.getIn());

        //logging at the end of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t|\t OrderID.: " +
                exchange.getIn().getHeader("orderID") +
                "  \t|\t New Header: is70percentPaid = " + exchange.getIn().getHeader("is70percentPaid").toString() +
                ", new Status:" + exchange.getIn().getBody(CarOrder.class).getStatus());
    }
}