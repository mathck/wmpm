package main.camel.routes;

import main.camel.beans.AddRecievedQuantity;
import main.camel.beans.OrderElementsBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderElementsRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {


        from("direct:orderElements")
                .routeId("orderElementsRoute")
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t|\t Order Nr.: ${header.orderID} \t|\t From QueryStock to PlanProduction")
                .log(LoggingLevel.INFO,"FILE", "HUI1 " + simple("${body}"))
                .bean(OrderElementsBean.class, "countDelay")
                .bean(OrderElementsBean.class, "makeOrder")
                .log(LoggingLevel.INFO,"FILE", "HUI2 " + simple("${body}"))
                .multicast()
                .to("jms:queue:waitDelivery?messageConverter=#myMessageConverter")
                .to("direct:makeOrder");

        from("direct:makeOrder").bean(OrderElementsBean.class, "makeOrder")
                .log(LoggingLevel.INFO,"FILE", "HUI3 " + simple("${body}"))
                .to("{{ftp.server}}");

        from("jms:queue:waitDelivery")
                .log(LoggingLevel.INFO,"FILE", "HUI4" + simple("${body}"))
                .delay(simple("${header.delay}")).log(LoggingLevel.INFO,"FILE", "HUI5 " + simple("${body}")).pollEnrich("jpa:Stock" +
                "?consumer.query=select s from Stock s where s.id=1&consumeDelete=false", new AddRecievedQuantity())
                .log(LoggingLevel.INFO,"FILE", "HUI6 " + simple("${body}"));
                //.to("jms:queue:planProduction");

    }
}