package main.camel.routes;

import main.camel.beans.QueryStockBean;
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
                    .to("seda:planProduction")
                .otherwise()
                    .to("direct:orderElements")
                .endChoice(); //TODO OrderElementsRoute
    }
}
