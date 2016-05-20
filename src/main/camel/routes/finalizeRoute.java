package main.camel.routes;

import main.beans.finalizeOrder;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by Mnishko Sergei on 20.05.2016.
 */
@Component
public class finalizeRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("seda: finalize.queque")
                .bean(finalizeOrder.class)
                .multicast()
                .to("file:C:\\del\\output1")
                .to("file:C:\\del\\output2");
    }
}
