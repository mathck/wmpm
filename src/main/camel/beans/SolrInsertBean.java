package main.camel.beans;

import main.model.Customer;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.solr.SolrConstants;
import org.apache.log4j.Logger;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

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

        long dateOfBirth=c.getDateOfBirth().getTime();
        long timezone=60*60*1000;
        Timestamp dateOfBirthPlusTimezone= new Timestamp(dateOfBirth+timezone);
        SolrInputDocument doc = new SolrInputDocument();

        doc.addField("ID", "1");
        doc.addField("FIRST_NAME", c.getFirstName());
        doc.addField("LAST_NAME", c.getLastName());
        doc.addField("DATE_OF_BIRTH", dateOfBirthPlusTimezone);
        doc.addField("STREET_NAME", c.getStreetName());
        doc.addField("HOUSE_NUMBER", c.getHouseNumber());
        doc.addField("CITY", c.getCity());

        template.sendBodyAndHeader("direct:SolrInsert", doc, SolrConstants.OPERATION, SolrConstants.OPERATION_INSERT);

    }

}
