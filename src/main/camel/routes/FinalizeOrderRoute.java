package main.camel.routes;


import main.camel.beans.FinalizeOrderBean;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 1.	Starting condition: Production finished an order
 2.	Process: Information on finished orders is received from production planning node.
             Payment will be requested using SOAP interface of invoice department.If successful, order will be dispatched.
 3.	Result: Payment will be requested. If successful, order will be dispatched

 */
@Component
public class FinalizeOrderRoute extends RouteBuilder {

    private FinalizeOrderBean finalizeOrderBean;

    @Autowired
    public FinalizeOrderRoute(FinalizeOrderBean finalizeOrderBean) {
        this.finalizeOrderBean = finalizeOrderBean;
    }


    @Override
    public void configure() throws Exception {
        from("direct:finalizeOrder")
        .to("jms:queue:dispatch");

        from("jms:queue:dispatch")
                .bean(finalizeOrderBean)
                .choice()
                .when(header("paid").isEqualTo(false))
                .to("jms:queue:dispatch")
                .otherwise()
                .to("direct:informCustomerAndAccept70Percent");

        from("direct:informCustomerAndAccept70Percent")
                .multicast()
                .to("seda:informCustomer")
                .to("direct:accept70percent");
    }
}
