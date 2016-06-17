package main.camel.routes;


import main.camel.beans.FinalizeOrderBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

/**
 1.	Starting condition: Production finished an order
 2.	Process: Information on finished orders is received from production planning node.
             Payment will be requested using SOAP interface of invoice department.If successful, order will be dispatched.
 3.	Result: Payment will be requested. If successful, order will be dispatched

 */
//@Component
public class FinalizeOrderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:finalizeOrder")
                .to("jms:queue:dispatch");

        from("jms:queue:dispatch")
                .routeId("FinalizeOrderRoute")
                .bean(FinalizeOrderBean.class)
                .choice()
                .when(header("paid").isEqualTo(false))
                .to("jms:queue:dispatch")
                .otherwise()
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t Order Nr.: ${header.orderID} \t|\t From FinalizeOrder to InformCustomerAndAccept70Percent")
                .to("direct:informCustomerAndAccept70Percent")
                .endChoice();

        from("direct:informCustomerAndAccept70Percent")
                .routeId("InformCus&Accept70Per")
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t Order Nr.: ${header.orderID} \t|\t From InformCustomerAndAccept70Percent to InformCustomer & Accept70Percent")
                .multicast()
                .to("seda:informCustomer")
                .to("direct:accept70percent");
    }
}
