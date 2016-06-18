package main.camel.routes;

import main.camel.beans.CreateOrderBean;
import main.camel.beans.SolrInsertBean;
import main.model.Customer;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
Testing route to configure JPA component
 */

@Component
public class GenerateCustomerRoute extends RouteBuilder {
    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Override
    public void configure() throws Exception {

        from("timer:start?period=10s&repeatCount=1&delay=2500")
                .routeId("GenerateCustomerRoute")
                .setBody().method(CreateOrderBean.class, "generateCustomer")
                .to("jpa:Customer")
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t CustomerID.: ${body.id} \t|\t New Customer Inserted")
                .bean(SolrInsertBean.class);

    }
}
