package main.model;

import main.model.enums.CarModel;
import main.model.enums.OrderStatus;

/**
 * Created by Polina on 15/05/2016.
 */
public class TestData {

    Customer customer;
    Order order;
    Stock stock;

    public Customer createCustomer () {
        customer = new Customer();

        customer.setId(1);
        customer.setEmail("demo-user@mail.com");
        customer.setFirstName("demo");
        customer.setLastName("user");
        customer.setAddress("test address");
        customer.setPhone("+43888888888");

        return customer;
    }


    public Order creatTestOrder () {
        order = new Order();

        order.setId(2);
        order.setCustomerFK(customer);
        order.setOrderDate(new java.sql.Timestamp(System.currentTimeMillis()));
        order.setStatus(OrderStatus.NEW);
        order.setCreditNeeded(true);
        order.setColor("red");
        order.setHorsepower(200);
        order.setModel(CarModel.VAN);

        return order;
    }

    public Stock createTestStock () {
        stock = new Stock();

        stock.setId(3);
        stock.setAvailableElementsForCabrio(5);
        stock.setAvailableElementsForCoupe(7);
        stock.setAvailableElementsForVan(3);
        stock.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
        return stock;
    }
}
