package main.camel.beans;

import main.dao.OrderDao;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Accept30PercentBean {

    private static final Logger LOGGER = Logger.getLogger(ProcessOrderBean.class);

    @Autowired
    private OrderDao orderDao;

    @Handler
    public String process (@Body String order)
    {
        return "New ELEMENTS WERE ORDERED - OK!";
    }

}
