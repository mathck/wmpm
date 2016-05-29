package main.camel.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Accept70PercentRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:accept70percent")
            .routeId("Accept70PercentRoute")
            .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t Order Nr.: ${header.orderID} \t|\t From Accept70Percent to HandOverOrder")
            .to("direct:handOverOrder");
    }
}
