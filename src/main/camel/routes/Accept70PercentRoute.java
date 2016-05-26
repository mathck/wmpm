package main.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Accept70PercentRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:accept70percent")
            .to("direct:handOverOrder");
    }
}
