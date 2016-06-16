package main.camel.beans;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Starting condition: CarOrder has been received
 * Result: CarOrder has been logged in the system, customer is informed
 * on order retrieval and payment is initialized
 *
 * Process: CarOrder information is extracted from the order and stored
 * in customer database. According to order information, check for financial
 * solvency is initialized or advance payment is initialized.
 */
@Component
public class ProcessOrderBean {

    private static final Logger LOGGER = Logger.getLogger("FILE");


    @Handler
    public void process (@Body String order, Exchange exchange)
    {
        //logging at the beginning of a process
        //LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t|\t Order Nr.: " + exchange.getIn().getHeader("orderID"));

        //logging at the end of a process
        //LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t|\t Order Nr.: " + exchange.getOut().getHeader("orderID") + "\t|\t New Header: creditNeeded = " + exchange.getOut().getHeader("creditNeeded").toString());

        //return order + "bar";

        //carOrderDao.insertOrder(order);
    }
}