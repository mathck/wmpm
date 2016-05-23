package main.camel.beans;

import main.dao.OrderDao;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by Michael on 23.05.2016.
 */
@Component
public class QueryStockBean {
    private static final Logger LOGGER = Logger.getLogger(ProcessOrderBean.class);

    @Autowired
    private OrderDao orderDao;

    @Handler
    public void process (@Body String order, Exchange exchange)
    {
        Random random = new Random();

        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
        exchange.getOut().setHeader("enoughElements",random.nextBoolean());

        LOGGER.debug(this.getClass().getName());
        LOGGER.debug("New Header:" + exchange.getOut().getHeaders().toString());

        //return order + "bar";

        //orderDao.insertOrder(order);
    }
}
