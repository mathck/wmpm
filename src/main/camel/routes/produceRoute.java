package main.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProduceRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
//        from("file:C:\\del\\input?noop=true")
//               .bean(ProduceBean.class)
//                .bean(ProduceBean.class, "waitforAssembling")
//                .to("seda: finalize.queque");
    }
}
