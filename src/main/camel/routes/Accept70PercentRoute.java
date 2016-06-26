package main.camel.routes;

import main.camel.beans.Accept70PercentBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Accept70PercentRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        errorHandler(deadLetterChannel("seda:errors"));
//        from("direct:accept70percent")
//            .routeId("Accept70PercentRoute")
//            .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t OrderID.: ${header.orderID} \t|\t From Accept70Percent to HandOverOrder")
//            .to("direct:handOverOrder");

        from("direct:accept70percent")
                .routeId("Accept70percentRouteEntrance")
                .to("jms:queue:Accept70Route?messageConverter=#accept70JMSConverter");

        from("jms:queue:Accept70Route?messageConverter=#accept70JMSConverter")
                .routeId("Accept70percentRoute")
                .bean(Accept70PercentBean.class)
                .choice()
                .when(header("is70percentPaid").isEqualTo(false))
                    .log(LoggingLevel.INFO, "FILE", "${routeId} \t\t\t|\t OrderID.: ${header.orderID} \t|\t From Accept70percentRouteDispatch to Accept70percentRouteDispatch - LOOP")
                    .to("jms:queue:Accept70Route?messageConverter=#accept70JMSConverter")
                .otherwise()
                    .log(LoggingLevel.INFO, "FILE", "${routeId} \t\t\t|\t OrderID.: ${header.orderID} \t|\t From Accept70percentRouteDispatch to InformCustomerAndAccept70Percent")
                    .wireTap("seda:informCustomer")
                    .to("direct:inform70Percent")
                .endChoice();

        from("direct:inform70Percent")
                .routeId("Accept70percentRouteLeaving")
                .log(LoggingLevel.INFO, "FILE", "${routeId} \t|\t Order Nr.: ${header.orderID} \t|\t From InformCustomerAndAccept70Percent to InformCustomer & HandOverOrder \t|\t")
                .to("direct:handOverOrder");
    }
}
