package main.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by Michael on 19.06.2016.
 */
@Component()
public class ExtSolvencyRoute extends RouteBuilder{

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Override
    public void configure() throws Exception {

        from("direct:ExtSolvency")
                .routeId("ExtSolvency")
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t OrderID.: ${header.orderID} \t\t|\t Datasource.: ${header.datasource} | Begin")
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
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t OrderID.: ${header.orderID} \t\t|\t Datasource.: ${header.datasource} | End")
                .to("direct:Consolidate");
    }
}
