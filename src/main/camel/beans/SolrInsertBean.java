package main.camel.beans;

import main.model.Customer;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.solr.SolrConstants;
import org.apache.log4j.Logger;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Component;

/**
 * Created by Michael on 05.06.2016.
 */
@Component
public class SolrInsertBean{

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Handler
    public void process(Exchange exchange, Customer c)
    {
        ProducerTemplate template = exchange.getContext().createProducerTemplate();
        LOGGER.info("FIRSTNAME DES KUNDEN X: " + c.getFirstName());

        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("ID", "1");
        doc.addField("FIRST_NAME", "Michael");
        doc.addField("LAST_NAME", "Lazarus");
        doc.addField("DATE_OF_BIRTH", "1993-02-09T00:00:00Z");
        doc.addField("STREET_NAME", "Roschegasse");
        doc.addField("HOUSE_NUMBER", "11/2");
        doc.addField("CITY", "Wien");

        template.sendBodyAndHeader("direct:SolrInsert", doc, SolrConstants.OPERATION, SolrConstants.OPERATION_INSERT);

    }

}
