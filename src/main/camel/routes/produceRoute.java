package main.camel.routes;

import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Mnishko Sergei on 20.05.2016.
 */

public class produceRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:C:\\del\\input?noop=true").to("file:C:\\del\\output");
    }
}
