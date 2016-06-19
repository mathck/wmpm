package main.camel.routes;

import main.camel.beans.Accept30PercentBean;
import main.camel.beans.MakeConfirmationBean;
import main.model.CarOrder;
import main.model.Customer;
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
                .to("jms:direct:makeConfirmation")
                .to("direct:queryStock");

        // @andrey TODO remove jpa call, and just take the customer from the body
        from("jms:direct:makeConfirmation")
            .routeId("MakeConfirmationRouter")
                    .pollEnrich("jpa:Customer" +
                            "?consumer.query=select c from Customer c where c.id=1&consumeDelete=false")
            .bean(MakeConfirmationBean.class, "makeConfirmation")
                    .multicast()
                    .to("file:backup/30percent/?fileName=${date:now:yyyyMMdd}-${in.header.orderID}.txt&autoCreate=true")
                    //.to("file://C:\\Users\\maland\\AppData\\Roaming\\SmartCompany\\30PercentConfirmation")
                    .to("seda:informCustomer"); // TODO route to clever
    }
}
