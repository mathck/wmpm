package main.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Accept70PercentRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

//        from("direct:accept70percent")
//            .routeId("Accept70PercentRoute")
//            .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t Order Nr.: ${header.orderID} \t|\t From Accept70Percent to HandOverOrder")
//            .to("direct:handOverOrder");


        from("direct:accept70percent")
//                .routeId("Accept70percentRoute-Entrance")
//                .to("jms:queue:Accept70percentRouteDispatch");
//
//        from("jms:queue:Accept70percentRouteDispatch").delay(5000)
//                .routeId("Accept70percentRouteDispatch")
//                .bean(Accept70PercentBean.class)
//                .choice()
//                .when(header("is70percentPaid").isEqualTo(false))
//                .log(LoggingLevel.INFO, "FILE", "${routeId} \t\t\t|\t Order Nr.: ${header.orderID} \t|\t From Accept70percentRouteDispatch to Accept70percentRouteDispatch - LOOP \t|\t ${body}")
//                .to("jms:queue:Accept70percentRouteDispatch")
//                .otherwise()
//                .log(LoggingLevel.INFO, "FILE", "${routeId} \t\t\t|\t Order Nr.: ${header.orderID} \t|\t From Accept70percentRouteDispatch to InformCustomerAndAccept70Percent \t|\t ${body}")
//                .to("jms:direct:informCustomerAndAccept70Percent")
//                .endChoice();
//
//        from("jms:direct:informCustomerAndAccept70Percent")
//                .routeId("Accept70percentRoute-Leaving")
//                .log(LoggingLevel.INFO, "FILE", "${routeId} \t\t|\t Order Nr.: ${header.orderID} \t|\t From InformCustomerAndAccept70Percent to InformCustomer & QueryStock \t|\t ${body}")
                .multicast()
                .to("direct:handOverOrder")
                .to("seda:informCustomer");

    }
}
