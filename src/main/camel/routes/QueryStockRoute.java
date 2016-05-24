package main.camel.routes;

import main.camel.beans.QueryStockBean;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryStockRoute extends RouteBuilder {

    private QueryStockBean queryStockBean;

    @Autowired
    public QueryStockRoute(QueryStockBean queryStockBean) {
        this.queryStockBean = queryStockBean;
    }

    private static final Logger LOGGER = Logger.getLogger(QueryStockRoute.class);

    @Override
    public void configure() throws Exception {

        LOGGER.info("taking route: queryStock -> planProduction");

        from("direct:queryStock")
                .routeId("QueryStockRoute")
                .bean(queryStockBean).choice()
                .when(header("enoughElements").isEqualTo(true))
                .to("direct:planProduction")
                .otherwise()
                .to("direct:orderElements").endChoice(); //TODO OrderElementsRoute
    }
}
