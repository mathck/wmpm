package main.model;

import main.model.enums.CarModel;
import main.model.enums.OrderStatus;

public class TestData {

    Customer customer;
    CarOrder carOrder;
//    Stock stock;

    public Customer createCustomer () {
        customer = new Customer();

        customer.setId(1);
        customer.setEmail("demo-user@mail.com");
        customer.setFirstName("demo");
        customer.setLastName("user");
        customer.setPhone("+43888888888");

        return customer;
    }


    public CarOrder creatTestOrder () {
        carOrder = new CarOrder();

        carOrder.setId(2);
        carOrder.setCustomerFK(customer);
        carOrder.setOrderDate(new java.sql.Timestamp(System.currentTimeMillis()));
        carOrder.setStatus(OrderStatus.NEW);
        carOrder.setCreditNeeded(true);
        carOrder.setColor("red");
        carOrder.setHorsepower(200);
        carOrder.setModel(CarModel.VAN);

        return carOrder;
    }

//    public Stock createTestStock () {
//        stock = new Stock();
//
//        stock.setId(3);
//        stock.setAvailableElementsForCabrio(5);
//        stock.setAvailableElementsForCoupe(7);
//        stock.setAvailableElementsForVan(3);
//        stock.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
//        return stock;
//    }
}
