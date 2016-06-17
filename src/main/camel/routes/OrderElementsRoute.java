package main.camel.routes;

import main.camel.beans.OrderElementsBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderElementsRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:orderElements")
                .routeId("OrderElementsRoute")
                .bean(OrderElementsBean.class)
                .to("{{ftp.server}}")
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t Order Nr.: ${body} \t|\t From OrderElements to PlanProduction");
                //.to("direct:planProduction");
    }
}