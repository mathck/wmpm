package main.camel.beans;

import main.dao.CustomerDao;
import main.dao.CarOrderDao;
import main.model.CarOrder;
import main.model.Customer;
import main.model.enums.CarModel;
import main.model.enums.OrderStatus;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CreateOrderBean {

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CarOrderDao carOrderDao;

    @Handler
    public void process(Exchange exchange)
    {
        Customer customer = new Customer();
        customer.setEmail("test@test.com");
        customer.setFirstName("Matthew");
        customer.setLastName("Gren");
        customer.setAddress("Karlsplatz 13, 1040 Wien");
        customer.setPhone("+4369915000596");

        customerDao.insertCustomer(customer);
        CarOrder order = new CarOrder();
        order.setCustomerFK(customer);
        order.setOrderDate(getOrderTime());
        order.setStatus(OrderStatus.NEW);
        order.setCreditNeeded(getRandomCreditNeeded());
        order.setColor(getRandomColor());
        order.setHorsepower(getRandomHorsepower());
        order.setModel(getRandomCarModel());

        carOrderDao.insertOrder(order);

        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
        exchange.getOut().setHeader("orderID", order.getId());

        //logging at the end of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t|\t Order Nr.: " + exchange.getOut().getHeader("orderID") + "  \t|\t New Header: orderID = " + exchange.getOut().getHeader("orderID").toString());
    }

    private java.sql.Timestamp getOrderTime() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }

    private boolean getRandomCreditNeeded() {
        return Math.random() < 0.5;
    }

    private String getRandomColor() {
        String[] colors = new String[] { "red", "green", "blue", "yellow", "black", "white", "silver" };
        return colors[new Random().nextInt(colors.length)];
    }

    private int getRandomHorsepower() {
        return new Random().nextInt(999 - 30) + 30;
    }

    private CarModel getRandomCarModel() {
        return CarModel.values()[(int)(Math.random()*(CarModel.values().length))];
    }
}
