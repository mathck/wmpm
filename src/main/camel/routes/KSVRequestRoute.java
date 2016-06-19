package main.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Michael on 16.06.2016.
 */
@Component()
public class KSVRequestRoute extends RouteBuilder {
    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Override
    public void configure() throws Exception {

        from("direct:KSVRequest")
                .routeId("KSVRequest")
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t OrderID.: ${header.orderID} \t\t|\t Datasource.: ${header.datasource} | Begin")
                .process( new Processor(){ public void process(Exchange exchange) throws Exception {
                    exchange.getIn().setBody(((String) exchange.getIn().getBody()).split(","));
                }
                })
                .transform(simple(
                        "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:localhost:smartcarcompany\">\n" +
                                "   <soapenv:Header/>\n" +
                                "   <soapenv:Body>\n" +
                                "      <urn:KSVRequest soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                                "         <firstName xsi:type=\"xsd:string\">${body[0]}</firstName>" +
                                "         <lastName xsi:type=\"xsd:string\">${body[1]}</lastName>\n" +
                                "         <street_name xsi:type=\"xsd:string\">${body[2]}</street_name>\n" +
                                "         <street_number xsi:type=\"xsd:string\">${body[3]}</street_number>\n" +
                                "         <city xsi:type=\"xsd:string\">${body[4]}</city>\n" +
                                "      </urn:KSVRequest>\n" +
                                "   </soapenv:Body>\n" +
                                "</soapenv:Envelope>"))
                .to("cxf://http://localhost:8080/KSV/?dataFormat=MESSAGE")
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t|\t OrderID.: ${header.orderID} \t\t|\t Datasource.: ${header.datasource} | End")
                .to("direct:Consolidate");
    }
}
