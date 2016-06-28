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
public class CreateOrderBean {

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Handler
    public void process(Exchange exchange)
    {
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());

        //Customer customer = generateCustomer();
        //LOGGER.info("CustomerID: " + customer.getId());
        //!!customerDao.insertCustomer(customer);
        //!!carOrderDao.insertOrder(order);
        //exchange.getOut().setHeader("orderID", order.getId());
        //exchange.getOut().setBody(generateCustomer());
        //logging at the end of a process
        //LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t\t|\t OrderID.: " + exchange.getOut().getHeader("orderID") + "  \t|\t New Header: orderID = " + exchange.getOut().getHeader("orderID").toString());
    }

    public Customer generateCustomer() {
        Customer newCustomer = new Customer();
        //newCustomer.setId(1);
        newCustomer.setEmail("mathck@gmail.com");
        newCustomer.setFirstName("Matthew");
        newCustomer.setLastName("Gren");
        newCustomer.setDateOfBirth(Timestamp.valueOf("1992-01-01 00:00:00"));
        newCustomer.setCity("Wien");
        newCustomer.setStreetName("Karlsplatz");
        newCustomer.setPostalCode("1040");
        newCustomer.setHouseNumber("13");
        newCustomer.setFraudHint(false);
        newCustomer.setInsuranceID(12345768);
        newCustomer.setPersonalID("uniqueFirstCustomer");
        newCustomer.setPhone("+4369915000596");

        return newCustomer;
    }

    public CarOrder generateOrder(Customer customer) {
        CarOrder newOrder = new CarOrder();
        newOrder.setCustomerFK(customer);
        newOrder.setOrderDate(getOrderTime());
        newOrder.setStatus(OrderStatus.NEW);
        newOrder.setCreditNeeded(getRandomCreditNeeded());
        newOrder.setColor(getRandomColor());
        newOrder.setHorsepower(getRandomHorsepower());
        newOrder.setModel(getRandomCarModel());
        return newOrder;
    }

    public CarOrder parseOrder(CarOrder order) {
        CarOrder newOrder = new CarOrder();
        newOrder.setId(order.getId());
        newOrder.setCustomerFK(order.getCustomerFK());
        newOrder.setOrderDate(order.getOrderDate());
        newOrder.setStatus(order.getStatus());
        newOrder.setCreditNeeded(order.getCreditNeeded());
        newOrder.setColor(order.getColor());
        newOrder.setHorsepower(order.getHorsepower());
        newOrder.setModel(order.getModel());
        return newOrder;
    }

    public Stock generateStock() {

        Stock stock = new Stock();
        stock.setStockName("CarStockDetails");
        stock.setAvaliableCount(7);

        return stock;
    }

    private java.sql.Timestamp getOrderTime() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }

    private boolean getRandomCreditNeeded() {
        return Math.random() < 0.7;
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
