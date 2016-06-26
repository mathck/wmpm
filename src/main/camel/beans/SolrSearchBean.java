package main.camel.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SolrSearchBean{

    @Handler
    public void process(Exchange exchange) throws Exception
    {
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
    }
}