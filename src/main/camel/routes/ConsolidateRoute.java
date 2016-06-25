package main.camel.routes;

import main.camel.beans.ArrayListAggregationStrategy;
import main.camel.beans.ConsolidateBean;
import main.camel.beans.SolrInsertBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component()
public class ConsolidateRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception {

        from("direct:Consolidate")
            .routeId("ConsolidateRoute")
            .aggregate(header("orderID"),new ArrayListAggregationStrategy()).completionSize(4)
            .setBody(simple("${body}"))
            .removeHeader("datasource")
            .bean(ConsolidateBean.class)
            .choice()
            .when(simple("${header.solvencyApproval} == 'false'"))
                .bean(SolrInsertBean.class)
            .otherwise()
                .to("direct:accept30percent")
            .end()
            .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t|\t OrderID.: ${header.orderID} \t|\t solvencyApproval: ${header.solvencyApproval}")
            .to("seda:informCustomer");
    }
}
