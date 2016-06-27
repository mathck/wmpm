package main.camel.routes;

import main.camel.beans.InformCustomerBean;
import main.model.CarOrder;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class InformCustomerRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception
    {

        //--------------------------------------------------
        // ROUTE
        //--------------------------------------------------
        from("direct:informCustomer")
                .routeId("InformCustomerRoute")
                .log(LoggingLevel.INFO, "FILE", "${routeId} \t\t\t\t|\t OrderID.: ${header.orderID} \t|\t Sending mail to ${body.getCustomerFK.getEmail} for status: ${body.getStatus}")
                .bean(InformCustomerBean.class)
                .to("smtp://smtp.gmail.com" +
                        "?port={{mail.port}}" +
                        "&username={{mail.username}}" +
                        "&password={{mail.password}}" +
                        "&subject=Your Order" +
                        "&mail.smtp.auth={{mail.smtp.auth}}" +
                        "&mail.smtp.starttls.enable={{mail.smtp.starttls.enable}}" +
                        "&to="+header("to"))
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t|\t OrderID.: ${header.orderID} \t|\t Saving Mail for Order ${header.orderID} and Status ${header.status} as ${date:now:yyyyMMdd}-${header.orderID}-${header.status}.txt")
                .to("file:backup/orders/mails/?fileName=${date:now:yyyyMMdd}-${header.orderID}-${header.status}.txt&autoCreate=true");;

    }
}
