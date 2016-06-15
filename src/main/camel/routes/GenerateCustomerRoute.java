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

        LOGGER.info("in configure GenerateCustomerRoute");

        from("timer:start?period=10s&repeatCount=3&delay=2500")
                .routeId("GenerateCustomerRoute")
                .setBody().method(CreateOrderBean.class, "generateCustomer")
                .to("jpa:Customer").log(LoggingLevel.INFO,"FILE", "Inserted new customer ${body.toString()}")
                .bean(SolrInsertBean.class)
                .setHeader("customerID",body().convertTo(Customer.class).method("getId"))
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t Customer Nr.: ${header.customerID} \t|\t From  to ")
                .to("direct:CheckCustomerData");
        /*
        .to(CheckCustomerData) is only temporary in this route!! Please Add the CheckCustomerData to the according position (after Process Order/Credit needed check) --> First Step in Solvency Check!
         */
    }
}
