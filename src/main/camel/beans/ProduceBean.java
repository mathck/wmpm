package main.camel.beans;

import main.model.CarOrder;
import main.model.enums.CarModel;
import main.model.enums.OrderStatus;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.PropertyInject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;

@Component
public class ProduceBean {
    private static final Logger LOGGER = Logger.getLogger(ProduceBean.class);

    @PropertyInject("{{delay.color.red}}")
    private int delayColorRed;

    @PropertyInject("{{delay.color.any}}")
    private int delayColorAny;

    @PropertyInject("{{delay.horsepower.high}}")
    private int delayHorsepowerHigh;

    @PropertyInject("{{delay.horsepower.low}}")
    private int delayHorsepowerLow;

    @PropertyInject("{{delay.model.coupe}}")
    private int delayModelCoupe;

    @PropertyInject("{{delay.model.any}}")
    private int delayModelAny;

    @Handler
    public void DefineDeliveryTime(Exchange exchange) {

        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t|\t Order Nr.: " + exchange.getIn().getBody(CarOrder.class).getId());
        exchange.setOut(exchange.getIn());

        Calendar calendar = Calendar.getInstance();
        CarOrder carorder = exchange.getIn().getBody(CarOrder.class);

        int delay = 0;

        //---------------------------------------
        // Car Color
        //---------------------------------------
        if (carorder.getColor().equals("red")) {
            calendar.add(Calendar.DATE, delay += delayColorRed);
        } else {
            calendar.add(Calendar.DATE, delay += delayColorAny);
        }

        //---------------------------------------
        // Car Horsepower
        //---------------------------------------
        if (carorder.getHorsepower() > 150) {
            calendar.add(Calendar.DATE, delay += delayHorsepowerHigh);
        } else {
            calendar.add(Calendar.DATE, delay += delayHorsepowerLow);
        }

        //---------------------------------------
        // Car Model
        //---------------------------------------
        if (carorder.getModel()== CarModel.COUPE) {
            calendar.add(Calendar.DATE, delay += delayModelCoupe);
        } else {
            calendar.add(Calendar.DATE, delay += delayModelAny);
        }

        carorder.setDeliveryDate(new Timestamp(calendar.getTimeInMillis()));
        carorder.setStatus(OrderStatus.ASSEMBLING);

        exchange.getOut().setHeader("delay", delay);
    }
}