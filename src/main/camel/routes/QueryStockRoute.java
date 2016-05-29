package main.camel.routes;

import main.camel.beans.QueryStockBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class QueryStockRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:queryStock")
                .routeId("QueryStockRoute")
                .bean(QueryStockBean.class)
                .choice()
                .when(header("enoughElements").isEqualTo(true))
                    .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t|\t Order Nr.: ${header.orderID} \t|\t From QueryStock to PlanProduction")
                    .to("seda:planProduction")
                .otherwise()
                    .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t|\t Order Nr.: ${header.orderID} \t|\t From QueryStock to OrderElements")
                    .to("direct:orderElements")
                .endChoice(); //TODO OrderElementsRoute
    }
}
