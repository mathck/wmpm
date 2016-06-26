package main.camel.routes;

import main.camel.beans.OrderSplitterBean;
import main.model.CarOrder;
import main.model.enums.OrderStatus;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CheckCustomerDataRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:CheckCustomerData")
            .routeId("CheckCustomerDataRoute")
            .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t OrderID.: ${header.orderID} \t|\t creditNeeded = ${body.creditNeeded}")
            .choice()
            .when(simple("${body.customerFK.fraudHint}")) // If fraudHint == true --> no credit will be guaranteed!
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t OrderID.: ${header.orderID} \t|\t FraudHint == TRUE | From CheckCustomerData To InformCustomer")
                .setHeader("solvencyApproval",simple("false"))
                .to("seda:informCustomer")
            .otherwise()
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t OrderID.: ${header.orderID} \t|\t Executing SolvencyCheck | From CheckCustomerData To CheckCustomerData-Split")
                .to("direct:CheckCustomerData-Split")
            .endChoice();

        from("direct:CheckCustomerData-Split")
            .routeId("CheckCustomerData-Split")
            .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t OrderID.: ${header.orderID} \t|\t ")
            .process( new Processor(){ public void process(Exchange exchange) throws Exception {
                CarOrder carOrder = (CarOrder) exchange.getIn().getBody();
                carOrder.setStatus(OrderStatus.CREDITCHECK);
                exchange.getIn().setBody(carOrder);
            }
            })
            .split()
                .method(OrderSplitterBean.class,"splitOrder")
                .parallelProcessing()
                .choice()
                .when(header("datasource").isEqualTo("KSV"))
                    .to("direct:KSVRequest")
                .when(header("datasource").isEqualTo("Schufa"))
                    .to("direct:SchufaRequest")
                .when(header("datasource").isEqualTo("ExtSol"))
                    .to("direct:ExtSolvency")
                .otherwise()
                    .to("direct:Solr")
            .end();
    }
}
