package main.camel.routes;

import main.camel.beans.InformCustomerBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class InformCustomerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("seda:informCustomer")
            .routeId("InformCustomerRoute")
            .bean(InformCustomerBean.class);
    }
}
