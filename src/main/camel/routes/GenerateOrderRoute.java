package main.camel.routes;

import main.camel.beans.CreateOrderBean;
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

        LOGGER.info("in configure GenerateOrderRoute");

        from("timer:start?period=20s&delay=2500").pollEnrich("jpa:Customer" +
                "?consumer.query=select c from Customer c where c.id = 1&consumeDelete=false")
                .routeId("GenerateOrderRoute")
                .setBody().method(CreateOrderBean.class, "generateOrder")
                .to("jpa:Order")
                .log(LoggingLevel.INFO,"FILE", "Retrieved customer ${body.toString()}");
    }
}
