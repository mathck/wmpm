package main.camel.routes;


import main.camel.beans.CreateOrderBean;
import main.camel.beans.GenerateOrderRESTBean;
import main.camel.beans.RESTInterfaceConverterBean;
import main.camel.beans.StockAggregationStrategy;
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
//                .pollEnrich("jpa:Customer" +
//                        "?consumer.query=select c from Customer c where c.id = 2&consumeDelete=false")
//                .bean(SergeiBean.class, "test");

        from("jetty:http://localhost:8181/order?httpMethodRestrict=POST")
                .routeId("RESTInterfaceOrderRouter")
                .bean(RESTInterfaceConverterBean.class)
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t| Request to generate an order")
                .unmarshal()
                .json(CarOrder.class, Views.Order.class)
                .setBody()
                .method(GenerateOrderRESTBean.class, "generateOrder")
                .bean(RESTInterfaceConverterBean.class, "test")
                .to("jpa:Order")
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t RESTCreateOrder \t|\t Received order for customer ${body.getCustomerFK.getFirstName} ${body.getCustomerFK.getLastName} \t|\t")
                .setHeader("orderID", body().convertTo(CarOrder.class).method("getId"))
                .setHeader("creditNeeded", body().convertTo(CarOrder.class).method("getCreditNeeded"))
                .wireTap("seda:backupOrder")
                .to("direct:processOrder");


    }

}
