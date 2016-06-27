package main.camel.routes;

import main.camel.beans.ProcessOrderBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProcessOrderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:processOrder")
            .routeId("ProcessOrderRoute")
            .bean(ProcessOrderBean.class)
            .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t|\t OrderID.: ${header.orderID} \t|\t From ProcessOrder to InformCustomer & CreditRouter")
            .multicast()
                .to("direct:informCustomer")
                .to("seda:creditRouter");

        from("seda:creditRouter")
            .routeId("CreditRouter")
            .choice()
                .when(simple("${properties:checkSolvency} == 'true' && ${header.creditNeeded} == 'true'"))
                    .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t\t|\t OrderID.: ${header.orderID} \t|\t CreditNeeded: Header-${header.creditNeeded}, Properties-${properties:checkSolvency} | From CreditRouter To CheckCustomerData")
                    .to("direct:CheckCustomerData")
                .otherwise()
                    .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t\t|\t OrderID.: ${header.orderID} \t|\t CreditNeeded: Header-${header.creditNeeded}, Properties-${properties:checkSolvency} | From CreditRouter To Accept30Percent")
                    .to("direct:accept30percent")
            .endChoice();
    }
}