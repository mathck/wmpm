package main.camel.routes;

import main.camel.beans.InformCustomerBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.util.jsse.KeyStoreParameters;
import org.apache.camel.util.jsse.SSLContextParameters;
import org.apache.camel.util.jsse.TrustManagersParameters;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InformCustomerRoute extends RouteBuilder {

    // TODO Mateusz: use properties component here

    // mail component doc: http://camel.apache.org/mail.html

    @Override
    public void configure() throws Exception
    {
        // the mail component takes the body and sends it

        String recipient = "mathck@gmail.com"; // for multiple may be seperated by ","
        String body = "Hi Mateusz, ich funktioniere :)";

        from("seda:informCustomer")
            .routeId("InformCustomerRoute")
            .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t Order Nr.: ${header.orderID} \t|\t ")
            .to("smtp://smtp.gmail.com" +
                    "?port=587" +
                    "&username=smartcarcompany@gmail.com" +
                    "&password=supersecuritypassword" +
                    "&mail.smtp.auth=true&mail.smtp.starttls.enable=true" + // required for TLS
                    "&to=" + recipient);
    }
}
