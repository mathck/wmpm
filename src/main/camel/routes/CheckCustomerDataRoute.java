package main.camel.routes;

import main.camel.beans.CreateOrderBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by Michael on 15.06.2016.
 */
@Component
public class CheckCustomerDataRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Override
    public void configure() throws Exception {

        LOGGER.info("in configure GenerateOrderRoute");

        from("timer:start?period=20s").pollEnrich("jpa:Customer" +
                "?consumer.query=select c from Customer c where c.id = 1&consumeDelete=false")
                .routeId("CheckCustomerDataRoute")
                .setBody().method(CreateOrderBean.class, "generateOrder")
                .to("jpa:Order")
                .log(LoggingLevel.INFO,"FILE", "Retrieved customer ${body.toString()}")
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t Order Nr.: ${header.orderID} \t|\t From CheckCustomerDataRoute to RequestDatasourcesRoute");

    }
}