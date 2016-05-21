package main.camel.routes;


import main.camel.beans.buildCar;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
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
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("processing... " + exchange.getIn().getBody(String.class));
                    }
                })
                .bean(buildCar.class, "buildCar")
                .to("seda: finalize.queque");
    }
}
