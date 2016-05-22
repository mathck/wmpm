package main.camel.beans;

import main.dao.CustomerDao;
import main.model.Customer;
import main.model.Order;
import main.model.enums.CarModel;
import main.model.enums.OrderStatus;
import org.apache.camel.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Creates the customer and the order randomly
 */
@Component
public class CreateOrderBean {

    @Autowired
    private CustomerDao customerDao;

    @Handler
    public Order process()
    {
        Customer customer = new Customer();
        customer.setEmail("mathck@mail.com");
        customer.setFirstName("Matthew");
        customer.setLastName("Gren");
        customer.setAddress("Karlsplatz 13, 1040 Wien");
        customer.setPhone("+4369915000596");

        customerDao.insertCustomer(customer);

        Order order = new Order();
        order.setCustomerFK(customer);
        order.setOrderDate(getOrderTime());
        order.setStatus(OrderStatus.NEW);
        order.setCreditNeeded(getRandomCreditNeeded());
        order.setColor(getRandomColor());
        order.setHorsepower(getRandomHorsepower());
        order.setModel(getRandomCarModel());

        return order;
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
