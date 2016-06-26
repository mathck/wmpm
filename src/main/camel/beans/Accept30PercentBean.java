package main.camel.beans;

import main.model.CarOrder;
import main.model.enums.OrderStatus;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Accept30PercentBean {

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Handler
    public void process(Exchange exchange) throws Exception {
        //getting information about payment
        //logging at the beginning of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t|\t Order Nr.: " +
                exchange.getIn().getHeader("orderID"));

        Random random = new Random();
        Boolean paymentSuccessful = random.nextBoolean();
        exchange.getIn().setHeader("is30percentPaid", paymentSuccessful );
        if (paymentSuccessful) {
            exchange.getIn().getBody(CarOrder.class).setStatus(OrderStatus.INITIALPAYMENTACCEPTED);
        }
        exchange.setOut(exchange.getIn());

        //logging at the end of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t|\t OrderID.: " + exchange.getOut().getHeader("orderID") + "  \t|\t New Header: is30percentPaid = " + exchange.getOut().getHeader("is30percentPaid").toString());
    }
}