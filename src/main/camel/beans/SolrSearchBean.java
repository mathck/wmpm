package main.camel.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.ProducerTemplate;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SolrSearchBean{

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Handler
    public void process(Exchange exchange) throws Exception
    {
        //logging at the beginning of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t|\t OrderID.: " + exchange.getIn().getHeader("orderID"));

        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
        ArrayList bodyList = new ArrayList();
        String responseXml;
        bodyList.add(exchange.getIn().getBody());

        ProducerTemplate template = exchange.getContext().createProducerTemplate();

        try {
            responseXml = (String) template.requestBody("direct:SolrSelectNode1",exchange.getIn().getBody());
        } catch (Exception e){
            responseXml = null;
        }

        if(responseXml == null) {

            try {
                responseXml = (String) template.requestBody("direct:SolrSelectNode2", exchange.getIn().getBody());
            } catch (Exception e) {
                responseXml = null;
            }
        }

        bodyList.add(responseXml);
        exchange.getOut().setBody(bodyList);

        //logging at the end of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t|\t OrderID.: " + exchange.getOut().getHeader("orderID") + "\t\t|\t ");
    }
}