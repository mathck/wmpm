package main.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;

/**
Testing route to configure JPA component
 */

public class RetrieveCustomerRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(GenerateCustomerRoute.class);

    @Override
    public void configure() throws Exception {

        LOGGER.info("ini configure GenerateCustomerRoute");

        /*from("jpa:main.model.Customer").id("1")
                .routeId("GenerateCustomerRoute")
                .bean(CreateOrderBean.class)
                .log(LoggingLevel.INFO,"FILE", "Retrieved customer ${body.id}")
                .to("jpa:main.model.Customer");*/
    }
}
