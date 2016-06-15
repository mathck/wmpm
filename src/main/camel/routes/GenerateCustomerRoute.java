package main.camel.routes;

import main.camel.beans.CreateOrderBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
Testing route to configure JPA component
 */
/*
@Component
public class GenerateCustomerRoute extends RouteBuilder {
    private static final Logger LOGGER = Logger.getLogger(GenerateCustomerRoute.class);

    @Override
    public void configure() throws Exception {

        LOGGER.info("in configure GenerateCustomerRoute");

        from("timer:start?period=10s&repeatCount=3")
                .routeId("GenerateCustomerRoute")
                .setBody().method(CreateOrderBean.class, "generateCustomer")
                .to("jpa:Customer").log(LoggingLevel.INFO,"FILE", "Inserted new customer ${body.toString()}");
    }
}*/
