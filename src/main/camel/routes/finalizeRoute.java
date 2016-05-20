package main.camel.routes;

import main.beans.finalizeOrder;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Mnishko Sergei on 20.05.2016.
 */
public class finalizeRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("seda: finalize.queque")
                .bean(finalizeOrder.class)
                .multicast()
                .to("direct:Customer")
                .to("direct:accounting");
    }
}
