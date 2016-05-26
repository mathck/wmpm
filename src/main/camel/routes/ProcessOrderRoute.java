package main.camel.routes;

import main.camel.beans.ProcessOrderBean;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProcessOrderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:processOrder")
            .bean(ProcessOrderBean.class)
            .multicast()
            .to("direct:informCustomer")
            .to("direct:creditRouter");

        from("direct:creditRouter")
            .choice()
                .when(header("creditNeeded"))
                    .to("direct:checkFinancialSolvency")
                .otherwise()
                    .to("direct:accept30percent")
            .endChoice();
    }
}
