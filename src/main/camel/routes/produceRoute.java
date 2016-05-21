package main.camel.routes;


import main.camel.beans.ProduceBean;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by Mnishko Sergei on 20.05.2016.
 */
@Component
public class produceRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:C:\\del\\input?noop=true")
               .bean(ProduceBean.class)
                .bean(ProduceBean.class, "waitforAssembling")
                .to("seda: finalize.queque");
    }
}
