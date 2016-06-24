package main.camel.routes;

import main.camel.beans.ArrayListAggregationStrategy;
import main.camel.beans.ConsolidateBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by Michael on 19.06.2016.
 */
@Component()
public class ConsolidateRoute extends RouteBuilder{

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Override
    public void configure() throws Exception {

        from("direct:Consolidate")
                .routeId("Consolidate")
                .aggregate(header("orderID"),new ArrayListAggregationStrategy()).completionSize(4)
                .setBody(simple("${body}"))
                .removeHeader("datasource")
                .bean(ConsolidateBean.class)
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t OrderID.: ${header.orderID} \t\t|\t solvencyApproval.: ${header.solvencyApproval}")
                //.log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t OrderID.: ${header.orderID} \t\t|\t ${body[0]}\t\t${body[1]}\t\t${body[2]}\t\t${body[3]}")
        ;
    }
}
