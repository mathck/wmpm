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

import main.camel.SmartCarConfig;
import main.model.Customer;
import main.model.Order;
import main.model.enums.CarModel;
import main.model.enums.OrderStatus;
import org.apache.camel.spring.Main;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class SmartCarApp extends Main {

    private static final Logger LOGGER = Logger.getLogger(SmartCarApp.class);

    @Autowired
    private SessionFactory sessionFactory;

    public Session session() {
        return sessionFactory.getCurrentSession();
    }

    public static void main(String... args) throws Exception {
        BasicConfigurator.configure();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SmartCarConfig.class);
        SmartCarApp smartCarApp = new SmartCarApp();
        smartCarApp.setApplicationContext(context);
        smartCarApp.testHSQLDB();
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

    public void testHSQLDB() {
        LOGGER.info("in test");
        // Add new Customer object
        Customer customer = new Customer();
        //customer.setId(1);
        customer.setEmail("demo-user@mail.com");
        customer.setFirstName("demo");
        customer.setLastName("user");
        customer.setAddress("test address");
        customer.setPhone("+43888888888");


        Order order = new Order();
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


        /*Customer customerRetrieved = new Customer();
        customerRetrieved = (Customer)session().createQuery("from Customer where id = :id")
                .setParameter("id", 1).uniqueResult();
        LOGGER.info(customerRetrieved.toString());*/
    }

}
