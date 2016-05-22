package main.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class FinalizeOrderRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(FinalizeOrderRoute.class);

    @Override
    public void configure() throws Exception {
//        from("seda: finalize.queque")
//                .bean(finalizeOrderBean.class)
//                .multicast()
//                .to("file:C:\\del\\output1")
//                .to("file:C:\\del\\output2");

        LOGGER.info("taking route: finalizeOrder -> accept70percent");

        from("direct:finalizeOrder")
            .to("seda:informCustomer")
            .to("direct:accept70percent");
    }
}
