package main.camel.routes;

import main.camel.beans.CreateOrderBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        /*// define and add the jetty component
        restConfiguration().component("jetty")
                .host("{{rest.jetty.host}}")
                .port("{{rest.jetty.port}}")
                .bindingMode(RestBindingMode.auto);

        // DEFINE BEHAVIOR ON DATA MODEL PROBLEMS
        onException(Exception.class).handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
                .setBody().simple("Invalid data values:\n${exception.message}");

        // DEFINE BEHAVIOR ON JSON SCHEMA PROBLEMS
        onException(UnrecognizedPropertyException.class).handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
                .setBody().constant("Invalid json data");*/

         /*from("timer:start?period=10000000s")
            .routeId("CreateOrderRoute")
            .bean(CreateOrderBean.class)
            //.bean(processOrderBean)
            .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t Order Nr.: ${header.orderID} \t|\t From CreateOrderRoute to ProcessOrderRoute")
            .to("direct:processOrder");*/
        from("timer:start?repeatCount=1")
                .routeId("GenerateStockRoute")
                .bean(CreateOrderBean.class, "generateStock")
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t Created Stock \t|\t Inserted new customer ${body}")
//                .multicast()
//                .to("direct:queryStock")
                .to("jpa:Stock");

        from("timer:start?repeatCount=1")//&delay=2500
                .log(LoggingLevel.INFO,"FILE","CustomerGeneration started.")
                .routeId("GenerateCustomerRoute")
                .setBody().method(CreateOrderBean.class, "generateCustomer")
                .to("jpa:Customer")
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t INITIALIZE \t|\t Inserted new customer ${body.getFirstName} ${body.getLastName}");

        from("timer:start?repeatCount=1").pollEnrich("jpa:Customer" +
                "?consumer.query=select c from Customer c where c.id = 1&consumeDelete=false")
                .routeId("GenerateOrderRoute")
                .setBody()
                .method(CreateOrderBean.class, "generateOrder")
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t Created order \t|\t Inserted order ${body}")
                .multicast()
                .to("direct:queryStock")
                .to("jpa:Order");
//                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t CreateOrder \t|\t Received order for customer ${body.getCustomerFK.getFirstName} ${body.getCustomerFK.getLastName}")
//                .setHeader("orderID", body().convertTo(CarOrder.class).method("getId"))
//                .setHeader("creditNeeded", body().convertTo(CarOrder.class).method("getCreditNeeded"))
//                .wireTap("seda:backupOrder")
//                .to("direct:processOrder");


        /*rest("/services/rest").put("/order").consumes("application/json")
                .type(CarOrder.class).produces("text/html")
                .to("direct:order_put");

        from("direct:order_put").routeId("REST")
            .log("Received REST message with a simple order")
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
            .to("direct:order_processing")
            .end();*/
    }
}
