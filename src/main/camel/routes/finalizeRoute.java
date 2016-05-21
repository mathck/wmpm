package main.camel.routes;


import main.camel.beans.finalizeOrderBean;
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
                .bean(finalizeOrderBean.class)
                .multicast()
                .to("file:C:\\del\\output1")
                .to("file:C:\\del\\output2");
    }
}
