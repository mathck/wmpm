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

        from("direct:OrderElements")
                .routeId("OrderElementsRoute")
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t|\t OrderID.: ${header.orderID} \t|\t From QueryStock to PlanProduction")
                .bean(OrderElementsBean.class, "countDelay")
                .multicast()
                .to("direct:makeOrder")
                .to("jms:queue:waitDelivery?messageConverter=#orderJMSConverter");

        from("direct:makeOrder")
                .bean(OrderElementsBean.class, "makeOrder")
                .to("{{ftp.server.production}}");

        from("jms:queue:waitDelivery?messageConverter=#orderJMSConverter").delay(simple("${header.delay}"))
                .pollEnrich("jpa:Stock" +
                "?consumer.query=select s from Stock s where s.id=1&consumeDelete=false", new AddRecievedQuantity())
                .to("jms:queue:planProduction?messageConverter=#orderJMSConverter");

    }
}