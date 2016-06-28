package main.camel.routes;

import main.camel.beans.ArrayListAggregationStrategy;
import main.camel.beans.ConsolidateBean;
import main.camel.beans.SolrInsertBean;
import main.model.CarOrder;
import main.model.enums.OrderStatus;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component()
public class ConsolidateRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception {

        from("direct:Consolidate")
            .routeId("ConsolidateRoute")
            .aggregate(header("orderID"),new ArrayListAggregationStrategy()).completionSize(4)
            .bean(ConsolidateBean.class)
            .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t|\t OrderID.: ${header.orderID} \t|\t solvencyApproval: ${header.solvencyApproval}")
            .process( new Processor(){ public void process(Exchange exchange) throws Exception {
                CarOrder carOrder = (CarOrder) exchange.getIn().getBody();
                carOrder.setStatus(OrderStatus.CREDITRESULT);
                exchange.getIn().setBody(carOrder);
            }
            })
            .choice()
            .when(simple("${header.solvencyApproval} == 'false'"))
                .bean(SolrInsertBean.class)
            .otherwise()
                .to("direct:accept30percent")
            .end()
            .to("direct:informCustomer");
    }
}
