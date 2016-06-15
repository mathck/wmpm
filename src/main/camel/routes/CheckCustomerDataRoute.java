package main.camel.routes;

import main.camel.beans.CreateOrderBean;
import main.camel.beans.SolrInsertBean;
import main.model.Customer;
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

        from("direct:CheckCustomerData")
                .routeId("CheckCustomerDataRoute")
                .pollEnrich("jpa:Customer" +
                "?consumer.query=select c from Customer c where c.id = ${header.customerID}&consumeDelete=false")
                .choice()
                .when(body().convertTo(Customer.class).method("getId"))
                    .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t|\t Order Nr.: ${header.orderID} \t|\t Credit is needed | From CreditRouter To CheckFinancialSolvency")
                    .to("direct:checkFinancialSolvency")
                .otherwise()
                    .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t|\t Order Nr.: ${header.orderID} \t|\t Credit is not needed | From CreditRouter To Accept30Percent")
                    .to("direct:accept30percent")
                .endChoice();
    }

}
