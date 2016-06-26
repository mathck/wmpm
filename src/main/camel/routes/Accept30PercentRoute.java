package main.camel.routes;

import main.camel.beans.Accept30PercentBean;
import main.camel.beans.MakeConfirmationBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Accept30PercentRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        errorHandler(deadLetterChannel("seda:errors"));
//        from("direct:accept30percent")
//                .routeId("Accept30percentRoute")
//                .bean(Accept30PercentBean.class)
//                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t OrderID.: ${header.orderID} \t|\t From Accept30Percent to QueryStock")
//                .to("direct:queryStock");

        from("direct:accept30percent")
                .routeId("Accept30percentRouteEntrance")
                .to("jms:queue:accept30Route?messageConverter=#accept30JMSConverter");

        from("jms:queue:accept30Route?messageConverter=#accept30JMSConverter")
                .routeId("Accept30Route")
                .bean(Accept30PercentBean.class)
                .choice()
                .when(header("is30percentPaid").isEqualTo(false))
                    .log(LoggingLevel.INFO, "FILE", "${routeId} \t\t\t\t\t|\t OrderID.: ${header.orderID} \t|\t From Accept30percentRouteDispatch to Accept30percentRouteDispatch - LOOP")
                    .to("jms:queue:accept30Route?messageConverter=#accept30JMSConverter")
                .otherwise()
                    .log(LoggingLevel.INFO, "FILE", "${routeId} \t\t\t\t\t|\t OrderID.: ${header.orderID} \t|\t From Accept30percentRouteDispatch to InformCustomerAndAccept30Percent")
                    .to("direct:informCustomerAndAccept30Percent")
                .endChoice();

        from("direct:informCustomerAndAccept30Percent")
                .routeId("Accept30percentRouteLeaving")
                .log(LoggingLevel.INFO, "FILE", "${routeId} \t\t|\t OrderID.: ${header.orderID} \t|\t From InformCustomerAndAccept30Percent to InformCustomer & QueryStock")
                .multicast()
                    .to("direct:storeConfirmation")
                    .to("direct:queryStock")
                    .to("seda:informCustomer");


        from("direct:storeConfirmation")
            .routeId("StoreConfirmationRouter")
            .bean(MakeConfirmationBean.class, "makeConfirmation")
                    .to("{{ftp.server.30PercentConfirmation}}");

    }
}
