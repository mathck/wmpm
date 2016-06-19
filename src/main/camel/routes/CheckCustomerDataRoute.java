package main.camel.routes;

import main.camel.beans.CreateOrderBean;
import main.camel.beans.OrderSplitterBean;
import main.model.CarOrder;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class CheckCustomerDataRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Override
    public void configure() throws Exception {

        from("direct:CheckCustomerData")
                .routeId("CheckCustomerDataRoute")
                .pollEnrich("jpa:CarOrder" +
                "?consumer.query=select o from CarOrder o where o.id = 1&consumeDelete=false")

                .setBody().method(CreateOrderBean.class, "parseOrder")
                .setHeader("orderID", body().convertTo(CarOrder.class).method("getId"))
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t OrderID.: ${header.orderID} \t\t|\t creditNeeded = ${body.creditNeeded}") //Current Body: ${body} | Current Header: ${headers}")
                .choice()
                .when(simple("true"))//body().convertTo(CarOrder.class).method("getCreditNeeded") Commented for working with Financial Solvency Check!
                    .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t OrderID.: ${header.orderID} \t\t|\t Credit is needed | From CheckCustomerData To CheckCustomerData-Split \t|\t")
                    .to("direct:CheckCustomerData-Split")
                .otherwise()
                    .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t OrderID.: ${header.orderID} \t\t|\t Credit is not needed | From CheckCustomerData To Accept30Percent \t|\t")
                    //.to("direct:accept30percent")
                    // Commented for working with Financial Solvency Check!
                .endChoice();

        from("direct:CheckCustomerData-Split")
                .routeId("CheckCustomerData-Split")
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t OrderID.: ${header.orderID} \t\t|\t")
                .split()
                    .method(OrderSplitterBean.class,"splitOrder")
                    .choice()
                    .when(header("datasource").isEqualTo("KSV"))
                        .to("direct:KSVRequest")
                    .when(header("datasource").isEqualTo("Schufa"))
                        .to("direct:SchufaRequest")
                    .when(header("datasource").isEqualTo("Solr"))
                        .to("direct:Recieve")
                    .otherwise()
                        .to("direct:Recieve")
                .end();

        from("direct:Recieve")
                .routeId("Recieve")

                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t OrderID.: ${header.orderID} \t\t|\t Datasource.: ${header.datasource} | ${body}");
    }
}