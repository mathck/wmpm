package main.camel.beans;

import main.model.CarOrder;
import main.model.enums.OrderStatus;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class FinalizeOrderBean {

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Handler
    public void process(Exchange exchange) throws Exception {

        //getting information about payment
        exchange.setOut(exchange.getIn());
        Random random = new Random();
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());

        Boolean testDriveCompleted = random.nextBoolean();
        exchange.getOut().setHeader("testDriveDone", testDriveCompleted);

        if (testDriveCompleted) {
            exchange.getOut().getBody(CarOrder.class).setStatus(OrderStatus.ASSEMBLINGFINISHED);
        }

        //logging at the end of a process
       // LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t|\t Order Nr.: " + exchange.getOut().getHeader("orderID") + "  \t|\t New Header: paid = " + exchange.getOut().getHeader("paid").toString());
    }
}
