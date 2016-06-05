package main.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class HandOverRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:handOverOrder")
            .to("seda:informCustomer");
    }
}
