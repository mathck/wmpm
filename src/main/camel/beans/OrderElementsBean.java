package main.camel.beans;

import main.dao.CarOrderDao;
import main.dao.CustomerDao;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderElementsBean {

    private static final Logger LOGGER = Logger.getLogger(TestCustomerBean.class);

    @Autowired
    CustomerDao customerDao;

/*    @Autowired
    StockDao stockDao;*/

    @Autowired
    CarOrderDao carOrderDao;

    @Handler
    public String process(@Body String order, Exchange exchange)
    {
        LOGGER.info(this.getClass().getName());
        return "New ELEMENTS WERE ORDERED - OK!";
    }
}
