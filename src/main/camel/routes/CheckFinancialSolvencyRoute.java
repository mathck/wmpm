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
            // should be deleted after implementation big part from Michi
            // next bean
            .bean(CheckFinancialSolvencyBean.class) //TODO Implement FinancialSolvencyBean
            .log(LoggingLevel.INFO,"FILE", "${routeId} \t|\t Order Nr.: ${header.orderID} \t|\t From CheckFinancialSolvency to InformCustomer & SolvencyApproval \t|\t")
            .multicast()
                .to("seda:informCustomer") //isn't clear why should we send here message
                .to("direct:solvencyApproval");

        from("direct:solvencyApproval")
                .routeId("SolvencyApproval")
                .choice()
                .when(header("solvencyApproval"))
                    .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t|\t Order Nr.: ${header.orderID} \t|\t From SolvencyApproval to Accept30Percent \t|\t")
                    .to("direct:accept30percent")
                .endChoice();
    }
}