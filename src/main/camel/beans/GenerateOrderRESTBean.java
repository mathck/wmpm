package main.camel.beans;

import main.model.CarOrder;
import main.model.Customer;
import main.model.Stock;
import main.model.enums.CarModel;
import main.model.enums.OrderStatus;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Random;

@Component
public class GenerateOrderRESTBean {

    private static final Logger LOGGER = Logger.getLogger("FILE");
    @Handler
    public void process(Exchange exchange)
    {
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
    }

    @Handler
    public CarOrder generateOrder(CarOrder order) {

        CarOrder newOrder = new CarOrder();
        newOrder = order;

        newOrder.setCustomerFK(order.getCustomerFK());
        newOrder.setOrderDate(getOrderTime());
        newOrder.setStatus(OrderStatus.NEW);

        int i = 8;
        return newOrder;
    }

    private java.sql.Timestamp getOrderTime() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }


}
