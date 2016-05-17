/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package main;

import main.config.SmartCarConfig;
import main.dao.CustomerDao;
import main.dao.OrderDao;
import main.model.Customer;
import org.apache.camel.spring.Main;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class SmartCarApp extends Main {

    private static final Logger LOGGER = Logger.getLogger(SmartCarApp.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CustomerDao customerDao;

    public static void main(String... args) throws Exception {
        BasicConfigurator.configure();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SmartCarConfig.class);
        SmartCarApp smartCarApp = context.getBean(SmartCarApp.class); // new
        //SmartCarApp smartCarApp = new SmartCarApp();
        // the above line was bad see:
        // http://stackoverflow.com/questions/3659720/using-spring-3-autowire-in-a-standalone-java-application
        smartCarApp.setApplicationContext(context);
        smartCarApp.testH2();
        smartCarApp.run();
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
        LOGGER.info("in test");

        Customer customer = new Customer();
        customer.setId(1);
        customer.setEmail("demo-user@mail.com");
        customer.setFirstName("demo");
        customer.setLastName("user");
        customer.setAddress("test address");
        customer.setPhone("+43888888888");
        LOGGER.info(customer.toString());

        //TODO: this customerDao is throwing NullPointerException, it shouldn't!
        LOGGER.info(customerDao.toString());
        customerDao.insertCustomer(customer);

        Customer customerRetrieved = new Customer();
        customerRetrieved = customerDao.getCustomer(1);
        LOGGER.info(customerRetrieved.toString());

        /*Order order = new Order();
        order.setId(2);
        LOGGER.info(order.getId());

        order.setCustomerFK(customer);
        LOGGER.info(order.getCustomerFK());

        order.setOrderDate(new java.sql.Timestamp(System.currentTimeMillis()));
        LOGGER.info(order.getOrderDate());

        order.setStatus(OrderStatus.NEW);
        LOGGER.info(order.getStatus());

        order.setCreditNeeded(true);
        LOGGER.info(order.getCreditNeeded());

        order.setColor("red");
        LOGGER.info(order.getColor());

        order.setHorsepower(200);
        LOGGER.info(order.getHorsepower());

        order.setModel(CarModel.VAN);
        LOGGER.info(order.getModel());

        session.save(order);
        session.getTransaction().commit();*/

    }

}
