package main.camel.routes;

import main.camel.beans.Accept30PercentBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Accept30PercentRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

//        from("direct:accept30percent")
//                .routeId("Accept30percentRoute")
//                .bean(Accept30PercentBean.class)
//                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t Order Nr.: ${header.orderID} \t|\t From Accept30Percent to QueryStock")
//                .to("direct:queryStock");


        from("direct:accept30percent")
                .routeId("Accept30percentRoute-Entrance")
                .to("jms:queue:Accept30percentRouteDispatch?messageConverter=#myMessageConverter");

        from("jms:queue:Accept30percentRouteDispatch").delay(5000)
                .routeId("Accept30percentRouteDispatch")
                .bean(Accept30PercentBean.class)
                .choice()
                .when(header("is30percentPaid").isEqualTo(false))
                .log(LoggingLevel.INFO, "FILE", "${routeId} \t\t\t|\t Order Nr.: ${header.orderID} \t|\t From Accept30percentRouteDispatch to Accept30percentRouteDispatch - LOOP")
                .to("jms:queue:Accept30percentRouteDispatch")
                .otherwise()
                .log(LoggingLevel.INFO, "FILE", "${routeId} \t\t\t|\t Order Nr.: ${header.orderID} \t|\t From Accept30percentRouteDispatch to InformCustomerAndAccept30Percent")
                .to("jms:direct:informCustomerAndAccept30Percent")
                .endChoice();

        from("jms:direct:informCustomerAndAccept30Percent")
                .routeId("Accept30percentRoute-Leaving")
                .log(LoggingLevel.INFO, "FILE", "${routeId} \t\t|\t Order Nr.: ${header.orderID} \t|\t From InformCustomerAndAccept30Percent to InformCustomer & QueryStock")
                .multicast()
                .to("direct:queryStock")
                .to("seda:informCustomer");


    }
}
