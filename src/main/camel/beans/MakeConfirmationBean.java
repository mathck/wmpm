package main.camel.beans;

import main.model.CarOrder;
import main.model.Customer;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MakeConfirmationBean {
    private static final Logger LOGGER = Logger.getLogger("FILE");

    public void makeConfirmation(@Body String body, Exchange exchange)
    {
        Customer customer = exchange.getIn().getBody(Customer.class);

        exchange.getOut().setHeaders(exchange.getIn().getHeaders());

        exchange.getOut().setHeader("fileName", customer.getId().toString() + ".txt");
        exchange.getOut().setBody("Dear " + customer.getFirstName() + " " + customer.getLastName()+ " " + "It is your confirmation of 30% payment. Your order is on the way!");
    }
}