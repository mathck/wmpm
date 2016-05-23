package main.camel.routes;


import main.camel.beans.finalizeOrderBean;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Mnishko Sergei on 20.05.2016.
 1.	Starting condition: Production finished an order
 2.	Process: Information on finished orders is received from production planning node. Payment will be requested using SOAP interface of invoice department.If successful, order will be dispatched.
 3.	Result: Payment will be requested. If successful, order will be dispatched

 */
@Component
public class FinalizeOrderRoute extends RouteBuilder {

    private finalizeOrderBean finalizeOrderBean;

    @Autowired
    public FinalizeOrderRoute(finalizeOrderBean finalizeOrderBean) {
        this.finalizeOrderBean = finalizeOrderBean;
    }

//    @Autowired
//    public finalizeRoute(finalizeOrderBean finalizeOrderBean){
//        this.finalizeOrderBean = finalizeOrderBean;
//    }

    @Override
    public void configure() throws Exception {
        from("file:C:\\del\\output")// from("direct:finalizeOrder")
                .bean(finalizeOrderBean)
                .to("file:C:\\del\\output2")
                .to("file:C:\\del\\output3");
    }
}
