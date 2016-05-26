package main.camel.routes;

import main.camel.beans.CheckFinancialSolvencyBean;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CheckFinancialSolvencyRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:checkFinancialSolvency")
                .bean(CheckFinancialSolvencyBean.class) //TODO Implement FinancialSolvencyBean
                .multicast()
                .to("direct:informCustomer")
                .to("direct:solvencyApproval");

        from("direct:solvencyApproval")
                .choice()
                .when(header("solvencyApproval"))
                    .to("direct:accept30percent")
                .endChoice();
    }
}