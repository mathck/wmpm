package main.camel.routes;

import main.camel.beans.OrderElementsBean;
import main.camel.beans.TestCustomerBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderElementsRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:orderElements")
                .routeId("OrderElementsRoute")
                .bean(OrderElementsBean.class)
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t Order Nr.: ${header.orderID} \t|\t From OrderElements to PlanProduction")
                .to("direct:planProduction");
    }
}