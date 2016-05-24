package main;

import main.config.SmartCarConfig;
import main.dao.CustomerDao;
import main.dao.OrderDao;
import main.dao.StockDao;
import main.model.Customer;
import main.model.Order;
import main.model.Stock;
import main.model.enums.CarModel;
import main.model.enums.ElementsName;
import main.model.enums.OrderStatus;
import org.apache.camel.spring.Main;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class SmartCarApp extends Main {

    private static final Logger LOGGER = Logger.getLogger(SmartCarApp.class);

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private StockDao stockDao;

    public static void main(String... args) throws Exception {
        BasicConfigurator.configure();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SmartCarConfig.class);
        SmartCarApp smartCarApp = context.getBean(SmartCarApp.class); // new


        //SmartCarApp smartCarApp = new SmartCarApp();
        // the above line was bad see:
        // http://stackoverflow.com/questions/3659720/using-spring-3-autowire-in-a-standalone-java-application


        smartCarApp.setApplicationContext(context);
//        smartCarApp.run();

      smartCarApp.testH2();
    }

    public void run() {
        BasicConfigurator.configure();
        super.enableHangupSupport();
        try {
            LOGGER.info("Server is running!");
            super.run();
        } catch (Exception e) {
            LOGGER.error("App can not be started, caused by " + e.getMessage(), e);
        }
    }

    public void testH2() {
//        System.out.println("HELLO11111111111111111111111111111111111111111111111111111111111111");
//        LOGGER.info("in test");
//
//        Customer customer = new Customer();
//        customer.setId(1);
//        customer.setEmail("demo-user@mail.com");
//        customer.setFirstName("demo");
//        customer.setLastName("user");
//        customer.setAddress("test address");
//        customer.setPhone("+43888888888");
//
//     customerDao.insertCustomer(customer);
//        System.out.println("HELL22222222222222222222222222222222222222222222222222222222222222222");
//
//
//        Customer customerRetrieved = new Customer();
//        customerRetrieved = customerDao.getCustomer(1);
//        System.out.println("test test test111111111111111111111");
//        LOGGER.info(customerRetrieved.toString());
//
//
//        System.out.println("test test test222222222222222222222222");

//        Stock stock = new Stock();
//
//        stock.setId(1);
//        stock.setElementsName(ElementsName.ELEMENTS_FOR_CABRIO);
//        stock.setAvaliableCount(100);
//        stock.setDeliveryTime(50);
//
//
//        System.out.println("test test test111111111111111111111111");
//
//        stockDao.insertStock(stock);
//
//        System.out.println("test test test222222222222222222222222");
//
//        Stock stockAnswer = new Stock();
//        stockAnswer = stockDao.getStock(1);
//
//        System.out.println("test test test3333333333333333333333333333333333333333");
//        LOGGER.info(stockAnswer.toString());
//        System.out.println("test test test444444444444444444444444444444444444444444");

//        Order order = new Order();
//        order.setId(2);
//        LOGGER.info(order.getId());
//
//        order.setCustomerFK(customer);
//        LOGGER.info(order.getCustomerFK());
//
//        order.setOrderDate(new java.sql.Timestamp(System.currentTimeMillis()));
//        LOGGER.info(order.getOrderDate());
//
//        order.setStatus(OrderStatus.NEW);
//        LOGGER.info(order.getStatus());
//
//        order.setCreditNeeded(true);
//        LOGGER.info(order.getCreditNeeded());
//
//        order.setColor("red");
//        LOGGER.info(order.getColor());
//
//        order.setHorsepower(200);
//        LOGGER.info(order.getHorsepower());
//
//        order.setModel(CarModel.VAN);
//        LOGGER.info(order.getModel());
//
//
//        session.save(order);
//        session.getTransaction().commit();

    }

}
