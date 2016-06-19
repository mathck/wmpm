package main.camel.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.ProducerTemplate;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by Michael on 05.06.2016.
 */
@Component
public class SolrSearchBean{

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Handler
    public void process(Exchange exchange)
    {
        //logging at the beginning of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t|\t Order Nr.: " + exchange.getIn().getHeader("orderID"));

        ProducerTemplate template = exchange.getContext().createProducerTemplate();

        String responseXml = (String) template.requestBody("direct:SolrSelect", "ID%3A1");

        LOGGER.info("+++++++++++++++++++++++++Solr response: " + responseXml + "\n");
        exchange.getOut().setBody(responseXml);

        //logging at the end of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t|\t Order Nr.: " + exchange.getOut().getHeader("orderID"));
        LOGGER.info(exchange.getOut().getBody());



    }

}
