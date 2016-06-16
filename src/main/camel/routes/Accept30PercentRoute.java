package main.camel.routes;

import main.camel.beans.Accept30PercentBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Accept30PercentRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:accept30percent")
                .routeId("Accept30percentRoute")
                .bean(Accept30PercentBean.class)
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t Order Nr.: ${header.orderID} \t|\t From Accept30Percent to QueryStock")
                .to("direct:queryStock");
    }
}
