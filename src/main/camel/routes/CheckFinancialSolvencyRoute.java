package main.camel.routes;

import main.camel.beans.InformCustomerBean;
import org.apache.camel.Predicate;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckFinancialSolvencyRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(CheckFinancialSolvencyRoute.class);

    @Override
    public void configure() throws Exception {

        LOGGER.info("taking route: checkFinancialSolvency -> accept30percent");

        from("direct:checkFinancialSolvency")
            .to("seda:informCustomer")
            .to("direct:accept30percent");
    }
}
