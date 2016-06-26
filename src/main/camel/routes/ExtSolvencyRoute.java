package main.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component()
public class ExtSolvencyRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception {

        onException(java.net.ConnectException.class)
            .log(LoggingLevel.ERROR,"FILE", "${routeId} \t\t|\t OrderID.: ${header.orderID} \t\t|\t Datasource: ${header.datasource} | Failed! - java.net.ConnectException")
            .to("direct:Consolidate");
        onException(Exception.class)
            .log(LoggingLevel.ERROR,"FILE", "${routeId} \t\t|\t OrderID.: ${header.orderID} \t\t|\t Datasource: ${header.datasource} | Failed! - Unknown Exception")
            .to("direct:Consolidate");

        from("direct:ExtSolvency")
            .routeId("ExtSolvencyRoute")
            .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t|\t OrderID.: ${header.orderID} \t|\t Datasource: ${header.datasource} | Begin")
            .process( new Processor(){ public void process(Exchange exchange) throws Exception {
                exchange.getIn().setBody(((String) exchange.getIn().getBody()).split(","));
            }
            })
            .transform(simple(
                    "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:localhost:smartcarcompany\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <urn:ExtSolRequest soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                        "         <firstName xsi:type=\"xsd:string\">${body[0]}</firstName>\n" +
                        "         <lastName xsi:type=\"xsd:string\">${body[1]}</lastName>\n" +
                        "         <insuranceID xsi:type=\"xsd:string\">${body[2]}</insuranceID>\n" +
                        "      </urn:ExtSolRequest>\n" +
                        "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>"))
            .to("cxf://http://localhost:8080/ExtSol/?dataFormat=MESSAGE")
            .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t|\t OrderID.: ${header.orderID} \t|\t Datasource: ${header.datasource} | End")
            .to("direct:Consolidate");
    }
}
