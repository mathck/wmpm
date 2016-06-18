package main.camel.routes;

import main.camel.beans.CreateOrderBean;
import main.model.CarOrder;
import main.model.Customer;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
Testing route to configure JPA component
 */

@Component
public class GenerateOrderRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(GenerateOrderRoute.class);

    @Override
    public void configure() throws Exception {

        from("timer:start?period=20s&delay=2500").pollEnrich("jpa:Customer" +
                "?consumer.query=select c from Customer c where c.id = 1&consumeDelete=false")
                .routeId("GenerateOrderRoute")
                .setBody().method(CreateOrderBean.class, "generateOrder")
                .to("jpa:Order")
                .setHeader("orderID",body().convertTo(CarOrder.class).method("getId"))
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t OrderID.: ${header.orderID} \t\t|\t Retrieved customer ${body.toString()}")
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t OrderID.: ${header.orderID} \t\t|\t From GenerateCustomerRoute to CheckCustomerData")
                .to("direct:CheckCustomerData");
        /*
        .to(CheckCustomerData) is only temporary in this route!! Please Add the CheckCustomerData to the according position (after Process Order/Credit needed check) --> First Step in Solvency Check!
         */;
    }
}
