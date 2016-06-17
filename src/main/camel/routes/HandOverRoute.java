package main.camel.routes;

import org.apache.camel.builder.RouteBuilder;

//@Component
public class HandOverRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:handOverOrder")
            .to("seda:informCustomer");
    }
}
