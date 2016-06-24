package main.camel.beans;

import main.model.CarOrder;
import main.model.Customer;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MakeConfirmationBean {
    private static final Logger LOGGER = Logger.getLogger("FILE");

    public void makeConfirmation(@Body String body, Exchange exchange)
    {
        exchange.setOut(exchange.getIn());
        CarOrder order = exchange.getIn().getBody(CarOrder.class);
        Customer customer = order.getCustomerFK();

        exchange.getOut().setHeader(exchange.FILE_NAME, customer.getFirstName() +"_order_"+order.getId()+".txt");
        exchange.getOut().setBody("Dear " + customer.getFirstName() + " " + customer.getLastName()+ " " + "It is your confirmation of 30% payment. Your order is on the way!");
    }
}