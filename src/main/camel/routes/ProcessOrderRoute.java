package main.camel.routes;

import main.camel.beans.ProcessOrderBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

//@Component
public class ProcessOrderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:processOrder")
            .routeId("ProcessOrderRoute")
            .bean(ProcessOrderBean.class)
            .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t Order Nr.: ${header.orderID} \t|\t From ProcessOrderRoute to InformCustomer & CreditRouter and Credit is: ${header.creditNeeded}")
            .multicast()
                .to("seda:informCustomer")
                .to("seda:creditRouter");

        from("seda:creditRouter")
            .routeId("CreditRouter")
            .choice()
                .when(header("creditNeeded"))
                    .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t|\t Order Nr.: ${header.orderID} \t|\t Credit needed: ${header.creditNeeded} | From CreditRouter To CheckFinancialSolvency")
                    .to("direct:checkFinancialSolvency")
                .otherwise()
                    .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t|\t Order Nr.: ${header.orderID} \t|\t Credit needed: ${header.creditNeeded} | From CreditRouter To Accept30Percent")
                    .to("direct:accept30percent")
            .endChoice();
    }
}
