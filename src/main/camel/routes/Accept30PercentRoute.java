package main.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Accept30PercentRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(Accept30PercentRoute.class);

    @Override
    public void configure() throws Exception {

        LOGGER.info("taking route: accept30percent -> queryStock");

        from("direct:accept30percent")
            .to("direct:queryStock");
    }
}
