package main.camel.beans;

import main.dao.CustomerDao;
import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestCustomerBean {

    private static final Logger LOGGER = Logger.getLogger(TestCustomerBean.class);

    @Autowired
    CustomerDao customerDao;

    @Handler
    public String process(@Body String order)
    {
        LOGGER.info(this.getClass().getName());
        return "TestCustomerBean - OK!";
    }
}
