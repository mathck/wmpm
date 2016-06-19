package main.camel.beans;

import main.model.CarOrder;
import main.model.enums.CarModel;
import main.model.enums.OrderStatus;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;

@Component
public class ProduceBean {
    private static final Logger LOGGER = Logger.getLogger(ProduceBean.class);


    @Handler
    public void DefineDeliveryTime(Exchange exchange) {

        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t|\t Order Nr.: " + exchange.getIn().getBody(CarOrder.class).getId());
        exchange.setOut(exchange.getIn());

        Calendar calendar = Calendar.getInstance();
        CarOrder carorder = exchange.getIn().getBody(CarOrder.class);

        int delay = 0;

        if (carorder.getColor() =="red"){
            calendar.add(Calendar.DATE, 15);
            delay = delay + 15;
         }else {
            calendar.add(Calendar.DATE, 25);
            delay = delay + 25;
        }
        if (carorder.getHorsepower() > 150){
            calendar.add(Calendar.DATE, 40);
            delay = delay + 40;
         }else{
            calendar.add(Calendar.DATE, 20);
            delay = delay + 20;
        }
        if (carorder.getModel()== CarModel.COUPE){
            calendar.add(Calendar.DATE, 60);
            delay = delay + 60;
        }else{
            calendar.add(Calendar.DATE, 40);
            delay = delay + 40;
        }
        carorder.setDeliveryDate(new Timestamp(calendar.getTimeInMillis()));
        carorder.setStatus(OrderStatus.ASSEMBLING);

        exchange.getOut().setHeader("delay", delay);
    }
}
