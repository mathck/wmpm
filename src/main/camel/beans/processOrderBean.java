package main.camel.beans;

import main.dao.OrderDao;
import main.model.Order;
import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Starting condition: Order has been received
 * Result: Order has been logged in the system, customer is informed
 * on order retrieval and payment is initialized
 *
 * Process: Order information is extracted from the order and stored
 * in customer database. According to order information, check for financial
 * solvency is initialized or advance payment is initialized.
 */
@Component
public class ProcessOrderBean {

    private static final Logger LOGGER = Logger.getLogger(ProcessOrderBean.class);

    @Autowired
    private OrderDao orderDao;

    @Handler
    public String process (@Body String order)
    {
        LOGGER.debug(this.getClass().getName());
        LOGGER.debug(order + "bar");
        return order + "bar";

        //orderDao.insertOrder(order);
    }
}