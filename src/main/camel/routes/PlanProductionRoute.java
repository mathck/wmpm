package main.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class PlanProductionRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(PlanProductionRoute.class);

    @Override
    public void configure() throws Exception {

        LOGGER.info("taking route: planProduction -> finalizeOrder");

        from("direct:planProduction")
            .to("direct:testOrder");
    }
}
