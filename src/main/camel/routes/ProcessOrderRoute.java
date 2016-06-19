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
            .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t Order Nr.: ${header.orderID} \t|\t From ProcessOrderRoute to InformCustomer & CreditRouter and Credit is: ${header.creditNeeded} \t|\t ${body}")
            .multicast()
                .to("seda:informCustomer")
                .to("seda:creditRouter");

        from("seda:creditRouter")
            .routeId("CreditRouter")
            .setHeader("creditNeeded", simple("false")) // TODO REMOVE ME, SOLR
            .choice()
                .when(header("creditNeeded"))
                    .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t|\t Order Nr.: ${header.orderID} \t|\t Credit needed: ${header.creditNeeded} | From CreditRouter To CheckFinancialSolvency \t|\t ${body}")
                    .to("direct:checkFinancialSolvency")
                .otherwise()
                    .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t|\t Order Nr.: ${header.orderID} \t|\t Credit needed: ${header.creditNeeded} | From CreditRouter To Accept30Percent \t|\t ${body}")
                    .to("direct:accept30percent")
            .endChoice();
    }
}
