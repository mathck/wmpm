package main.camel.routes;

import main.camel.beans.CheckFinancialSolvencyBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CheckFinancialSolvencyRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:checkFinancialSolvency")
                .routeId("CheckFinancialSolvencyRoute")
                .bean(CheckFinancialSolvencyBean.class) //TODO Implement FinancialSolvencyBean
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t|\t Order Nr.: ${header.orderID} \t|\t From CheckFinancialSolvency to InformCustomer & SolvencyApproval")
                .multicast()
                .to("seda:informCustomer")
                .to("direct:solvencyApproval");

        from("direct:solvencyApproval")
                .routeId("SolvencyApproval")
                .choice()
                .when(header("solvencyApproval"))
                    .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t Order Nr.: ${header.orderID} \t|\t From SolvencyApproval to Accept30Percent")
                    .to("direct:accept30percent")
                .endChoice();
    }
}