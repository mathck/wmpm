package main.camel.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class InformCustomerRoute extends RouteBuilder {

    // mail component doc: http://camel.apache.org/mail.html

    @Override
    public void configure() throws Exception
    {
        // the mail component takes the body and sends it

        String recipient = "mathck@gmail.com"; // for multiple may be seperated by ","

        //--------------------------------------------------
        // ROUTE
        //--------------------------------------------------
        from("seda:informCustomer")
                .routeId("InformCustomerRoute")
                .log(LoggingLevel.INFO, "FILE", "${routeId} \t\t\t\t|\t Order Nr.: ${header.orderID} \t|\t");
//prevent the spam machine
//_______________________________________________________________________________________________________________
//                .setBody(simple(getEmailBody("name", "status")))// TODO insert customerName and orderStatus
//                /*.process(new Processor() { TODO Andr
//                    @Override
//                    public void process(Exchange exchange) throws Exception {
//
//                        exchange.getIn().addAttachment("logo.jpeg", new DataHandler(new FileDataSource("C:\\logo.jpeg")));
//                    }
//                })*/
//                .to("smtp://smtp.gmail.com" +
//                        "?port={{mail.port}}" +
//                        "&username={{mail.username}}" +
//                        "&password={{mail.password}}" +
//                        "&subject=Your Order" +
//                        "&mail.smtp.auth={{mail.smtp.auth}}" +
//                        "&mail.smtp.starttls.enable={{mail.smtp.starttls.enable}}" +
//                        "&to=" + recipient);
//_______________________________________________________________________________________________________________
/*
        from("seda:informCustomerClever") TODO Andr
                .routeId("InformCustomerRouteClever")
                .log(LoggingLevel.INFO, "FILE", "${routeId} \t\t\t\t|\t Order Nr.: ${header.orderID} \t|\t ")
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

    private String getEmailBody(String customerName, String orderStatus) {
        return "Dear " + customerName + ",\n" +
                "We are pleased to inform you that your order status is " + orderStatus +
                "\n\n" +
                "Kind regards,\n" +
                "your smart car company bot";
    }
}
