package main.camel.beans;

import main.dao.CustomerDao;
import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import main.dao.CustomerDao;
import main.model.Customer;
import main.model.Order;
import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Created by maland on 24.05.2016.
 */
@Component
public class TestCustomerBean {

    private static final Logger LOGGER = Logger.getLogger(TestCustomerBean.class);

    @Autowired
    CustomerDao customerDao;

    @Handler
    public String process(@Body String order)
    {
        return "TestCustomerBean - OK!";
    }
}
