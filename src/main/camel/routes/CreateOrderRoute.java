package main.camel.routes;

import main.camel.beans.ControlBusBean;
import main.camel.beans.CreateOrderBean;
import main.camel.beans.OrderElementsBean;
import main.model.CarOrder;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.PropertyInject;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        errorHandler(deadLetterChannel("seda:errors"));

        from("timer:start?repeatCount=1")
                .routeId("GenerateStockRoute")
                .bean(CreateOrderBean.class, "generateStock")
                .to("jpa:Stock");

        /*from("timer:start?period=3s&delay=2500")
                .routeId("ControlBus")
                .bean(ControlBusBean.class, "process")
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t Control Bus \t|\t Control Bus performing analysis \t|\t")
                .to("file:backup/orders/?fileName=${date:now:yyyyMMdd}-${in.header.orderID}.txt&autoCreate=true");*/

        from("timer:start?period=5s&repeatCount=1&delay=2500")
                .choice()
                    .when(simple("${properties:settings.automaticGeneration} == 'true'"))
                        .routeId("GenerateCustomerRoute")
                        .setBody().method(CreateOrderBean.class, "generateCustomer")
                        .to("jpa:Customer")
                        .log(LoggingLevel.INFO, "FILE", "${routeId} \t\t\t|\t INITIALIZE \t|\t Inserted new customer ${body.getFirstName} ${body.getLastName} \t|\t")
                .end();

        from("timer:start?period=5s&repeatCount=10&delay=2500")
                .choice()
                    .when(simple("${properties:settings.automaticGeneration} == 'true'"))
                        .pollEnrich("jpa:Customer?consumer.query=select c from Customer c where c.id = 1&consumeDelete=false")
                        .bean(ControlBusBean.class, "process")
                        .routeId("GenerateOrderRoute")
                        .setBody()
                        .method(CreateOrderBean.class, "generateOrder")
                        .to("jpa:Order")
                        .log(LoggingLevel.INFO, "FILE", "${routeId} \t\t\t\t|\t CreateOrder \t|\t Received order for customer ${body.getCustomerFK.getFirstName} ${body.getCustomerFK.getLastName} \t|\t")
                        .setHeader("orderID", body().convertTo(CarOrder.class).method("getId"))
                        .setHeader("creditNeeded", body().convertTo(CarOrder.class).method("getCreditNeeded"))
                        .wireTap("seda:backupOrder")
                        .to("direct:processOrder")
                .end();
    }
}