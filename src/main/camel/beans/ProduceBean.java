package main.camel.beans;

import main.dao.OrderDao;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Mnishko Sergei on 20.05.2016.
 */
@Component
public class ProduceBean {
    private static final Logger LOGGER = Logger.getLogger(ProduceBean.class);

    @Autowired
    OrderDao orderDao;

    @Handler
    public String SchedulMachinetime(Exchange exchange) {
//         if (orderDao.getOrder(2).getColor()=="red"){
//            //time=time+1;
//         }else if (orderDao.getOrder(2).getHorsepower()>150){
//             //time=time+1;
//         }
        return "Scheduling has been completed";
    }

    public String waitforAssembling(Exchange exchange){
        return exchange.getIn().getBody().toString() + " has been produced";
    }
}
