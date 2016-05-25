package main.camel.beans;

import main.dao.CarOrderDao;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class QueryStockBean {
    private static final Logger LOGGER = Logger.getLogger(ProcessOrderBean.class);

    @Autowired
    private CarOrderDao carOrderDao;

    @Handler
    public void process (Exchange exchange)
    {

        int orderId=(int)exchange.getIn().getHeader("orderID");
        Random random = new Random();
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
        exchange.getOut().setHeader("enoughElements", random.nextBoolean());
        carOrderDao.getOrder(orderId);

        LOGGER.info(this.getClass().getName());
        LOGGER.info("New Header:" + exchange.getOut().getHeaders().toString());

    }
}
