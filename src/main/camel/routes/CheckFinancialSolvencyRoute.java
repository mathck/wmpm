package main.camel.routes;

import main.camel.beans.CheckFinancialSolvencyBean;
import main.camel.beans.InformCustomerBean;
import main.camel.beans.ProcessOrderBean;
import org.apache.camel.Predicate;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckFinancialSolvencyRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(CheckFinancialSolvencyRoute.class);
    private CheckFinancialSolvencyBean checkFinancialSolvencyBean;
    private InformCustomerBean informCustomerBean;

    @Autowired
    public CheckFinancialSolvencyRoute(CheckFinancialSolvencyBean checkFinancialSolvencyBean, InformCustomerBean informCustomerBean) {
        this.checkFinancialSolvencyBean = checkFinancialSolvencyBean;
        this.informCustomerBean = informCustomerBean;
    }

    @Override
    public void configure() throws Exception {

        //LOGGER.info("taking route: checkFinancialSolvency -> accept30percent + informCustomer");

        from("direct:checkFinancialSolvency")
                .bean(checkFinancialSolvencyBean) //TODO Implement FinancialSolvencyBean
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
