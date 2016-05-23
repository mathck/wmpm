package main.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class QueryStockRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(QueryStockRoute.class);

    @Override
    public void configure() throws Exception {

        LOGGER.info("taking route: queryStock -> planProduction");

        from("direct:queryStock")
            .choice()
                .when(header("enoughElements"))
                .to("direct:planProduction")
                //.otherwise()
                //.to("direct:orderElements") //TODO OrderElementsRoute
                .endChoice();
    }
}
