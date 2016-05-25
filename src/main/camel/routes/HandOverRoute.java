package main.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class HandOverRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(HandOverRoute.class);

    @Override
    public void configure() throws Exception {

        //LOGGER.info("taking route: handOverOrder -> informCustomer");

        from("direct:handOverOrder")
            .to("direct:informCustomer");
        //And that's it.
    }
}
