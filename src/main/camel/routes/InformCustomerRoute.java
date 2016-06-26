package main.camel.routes;

import main.model.CarOrder;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class InformCustomerRoute extends RouteBuilder {

    // mail component doc: http://camel.apache.org/mail.html

    @Override
    public void configure() throws Exception
    {
        // the mail component takes the body and sends it

        //String recipient = "mathck@gmail.com"; // for multiple may be seperated by ","

        //--------------------------------------------------
        // ROUTE
        //--------------------------------------------------
        from("seda:informCustomer")
                .routeId("InformCustomerRoute")
                //.setBody(simple(getEmailBody("name", "status")))// TODO insert customerName and orderStatus
                //.log(LoggingLevel.INFO, "FILE", "${routeId}\t\t\t\t|\t Order Nr.: ${header.orderID} 	|	Sending mail - Body is: ${body}")
                .log(LoggingLevel.INFO, "FILE", "${routeId} \t\t\t\t|\t Order Nr.: ${header.orderID} \t|\t Sending mail to ${body.getCustomerFK.getEmail} for status: ${body.getStatus}")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        CarOrder carOrder = exchange.getIn().getBody(CarOrder.class);
                        exchange.getOut().setBody(exchange.getIn().getBody());
                        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
                        exchange.getOut().setHeader("to", carOrder.getCustomerFK().getEmail());
                        exchange.getOut().setBody("Dear " + carOrder.getCustomerFK().getFirstName() + ",\n" +
                                "We are pleased to inform you that your order status is " + carOrder.getStatus() +
                                "\n\n" +
                                "Kind regards,\n" +
                                "your smart car company bot");

                    }
                })
                .to("smtp://smtp.gmail.com" +
                        "?port={{mail.port}}" +
                        "&username={{mail.username}}" +
                        "&password={{mail.password}}" +
                        "&subject=Your Order" +
                        "&mail.smtp.auth={{mail.smtp.auth}}" +
                        "&mail.smtp.starttls.enable={{mail.smtp.starttls.enable}}" +
                        "&to="+header("to"));

        /*
        from("seda:informCustomerClever") TODO Andr
                .routeId("InformCustomerRouteClever")
                .log(LoggingLevel.INFO, "FILE", "${routeId} \t\t\t\t|\t OrderID.: ${header.orderID} \t|\t ")
          //      .setBody(simple(getEmailBody("name", "status")))// TODO insert customerName and orderStatus
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        Customer customer = exchange.getIn().getBody(Customer.class);

                        String path = new String();
                        path = "C:\\Users\\maland\\AppData\\Roaming\\SmartCompany\\30PercentConfirmation" + "\\"+ exchange.getIn().getHeader("fileName").toString();

                        exchange.getIn().addAttachment(exchange.getIn().getHeader("fileName").toString(), new DataHandler(new FileDataSource(path)));
                    }
                })
                .to("smtp://smtp.gmail.com" +
                        "?port={{mail.port}}" +
                        "&username={{mail.username}}" +
                        "&password={{mail.password}}" +
                        "&subject=Your Order" +
                        "&mail.smtp.auth={{mail.smtp.auth}}" +
                        "&mail.smtp.starttls.enable={{mail.smtp.starttls.enable}}" +
                        "&to=" + recipient);
*/
    }

/*    private String getEmailBody(Exchange exchange) {
        CarOrder order = ((CarOrder) exchange.getIn().getBody());
        exchange.getOut().setBody(exchange.getIn().getBody());
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
        exchange.getOut().setHeader("recipient", order.getCustomerFK().getEmail());
        return "Dear " + order.getCustomerFK().getFirstName() + ",\n" +
                "We are pleased to inform you that your order status is " + order.getStatus() +
                "\n\n" +
                "Kind regards,\n" +
                "your smart car company bot";

    }*/
}
