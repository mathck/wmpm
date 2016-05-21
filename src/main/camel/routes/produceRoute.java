package main.camel.routes;



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
               // .bean(, "doSomething")
                .to("seda: finalize.queque");
    }
}
