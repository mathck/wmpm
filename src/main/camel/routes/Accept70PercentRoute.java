package main.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Accept70PercentRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(Accept70PercentRoute.class);

    @Override
    public void configure() throws Exception {

        LOGGER.info("taking route: accept70percent -> handOverOrder");

        from("direct:accept70percent")
            .to("direct:handOverOrder");
    }
}
