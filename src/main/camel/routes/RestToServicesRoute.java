package main.camel.routes;


import main.camel.beans.*;
import main.model.CarOrder;
import main.model.Customer;
import main.model.Views;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.codehaus.jackson.map.annotate.JsonView;
import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;
import org.springframework.stereotype.Component;
import org.apache.camel.Exchange;


@Component
public class RestToServicesRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {


        from("jetty:http://localhost:8181/customer?httpMethodRestrict=POST")
                .routeId("RESTInterfaceCustomerRouter")
                .bean(RESTInterfaceConverterBean.class)
                .log(LoggingLevel.INFO,"FILE","${routeId} \t\t| Request to generate a customer")
                .unmarshal()
                    .json(Customer.class, Views.Customer.class)
                .to("jpa:Customer")
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t INITIALIZE \t|\t Inserted new customer ${body.getFirstName} ${body.getLastName} \t|\t");;

        from("jetty:http://localhost:8181/order?httpMethodRestrict=POST")
                .routeId("RESTInterfaceOrderRouter")
                .bean(RESTInterfaceConverterBean.class)
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t| Request to generate an order")
                .unmarshal()
                .json(CarOrder.class, Views.Order.class)
                .setBody()
                .method(GenerateOrderRESTBean.class, "generateOrder")
                .to("jpa:Order")
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t RESTCreateOrder \t|\t Received order for customer ${body.getCustomerFK.getFirstName} ${body.getCustomerFK.getLastName} \t|\t")
                .setHeader("orderID", body().convertTo(CarOrder.class).method("getId"))
                .setHeader("creditNeeded", body().convertTo(CarOrder.class).method("getCreditNeeded"))
                .wireTap("seda:backupOrder")
                .bean(ControlBusBean.class, "process")
                .to("direct:processOrder");


    }

}
