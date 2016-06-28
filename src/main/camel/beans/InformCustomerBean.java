package main.camel.beans;

import main.model.CarOrder;
import main.model.enums.OrderStatus;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.apache.xpath.operations.Bool;
import org.springframework.stereotype.Component;

import java.util.Random;

import static main.model.enums.OrderStatus.NEW;

@Component
public class InformCustomerBean {

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Handler
    public void process(Exchange exchange) throws Exception {
        CarOrder carOrder = exchange.getIn().getBody(CarOrder.class);
        exchange.getIn().setHeader("to", carOrder.getCustomerFK().getEmail());
        exchange.getIn().setHeader("status", carOrder.getStatus());

        String status = "";

        switch(carOrder.getStatus()){
            case NEW:
                status = "your order has been received.";
                break;
            case INITIALPAYMENTACCEPTED:
                status = "your initial payment has been transferred.";
                break;
            case CREDITRESULT:
                if ((Boolean)exchange.getIn().getHeader("solvencyApproval")) {
                    status = "your credit check yielded the result: Passed!";
                }else {
                    status = "your order has been received. Unfortunately your credit was denied. Please contact us.";
                }
                break;
            case ASSEMBLING:
                status = "your car is currently being assembled.";
                break;
            case ASSEMBLINGFINISHED:
                status = "your car has finished the assembling process.";
                break;
            case PAYMENTCOMPLETED:
                status = "your payment has been received in full.";
                break;
            case DELIVERED:
                status = "your car is on its way to you. Enjoy and drive safely!";

        }

        exchange.getIn().setBody("Dear " + carOrder.getCustomerFK().getFirstName() + ",\n" +
                "We are pleased to inform you that  " + status +
                "\n\n" +
                "Kind regards,\n" +
                "your smart car company bot");
    }
}
