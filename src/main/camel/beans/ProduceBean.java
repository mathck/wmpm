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
    public void DefineDeliveryTime(Exchange exchange) {
//        Calendar calendar = Calendar.getInstance();
//        int id = 1;
//        //int id = (int) exchange.getIn().getBody().toString();
//        if (orderDao.getOrder(id).getColor() =="red"){
//            calendar.add(Calendar.DATE, 15);
//         }else {
//            calendar.add(Calendar.DATE, 25);
//        }
//        if (orderDao.getOrder(id).getHorsepower() > 150){
//            calendar.add(Calendar.DATE, 40);
//         }else{
//            calendar.add(Calendar.DATE, 20);
//        }
//        if (orderDao.getOrder(id).getModel()== COUPE){
//            calendar.add(Calendar.DATE, 60);
//        }else{
//            calendar.add(Calendar.DATE, 40);
//        }
//        orderDao.getOrder(id).setDeliveryDate(new Timestamp(calendar.getTimeInMillis()));
//        orderDao.getOrder(id).setStatus(OrderStatus.ASSEMBLING);
        System.out.println("TEST 1");
    }
}
