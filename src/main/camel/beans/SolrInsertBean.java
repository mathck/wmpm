package main.camel.beans;

import main.model.CarOrder;
import main.model.Customer;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.solr.SolrConstants;
import org.apache.log4j.Logger;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class SolrInsertBean{

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Handler
    public void process(Exchange exchange, CarOrder carOrder)
    {
        ProducerTemplate template = exchange.getContext().createProducerTemplate();

        long dateOfBirth=carOrder.getCustomerFK().getDateOfBirth().getTime();
        long timezone = 60 * 60 * 1000;
        Timestamp dateOfBirthPlusTimezone= new Timestamp(dateOfBirth+timezone);
        SolrInputDocument doc = new SolrInputDocument();

        doc.addField("ID", carOrder.getId());
        doc.addField("FIRST_NAME", carOrder.getCustomerFK().getFirstName());
        doc.addField("LAST_NAME", carOrder.getCustomerFK().getLastName());
        doc.addField("DATE_OF_BIRTH", dateOfBirthPlusTimezone);
        doc.addField("STREET_NAME", carOrder.getCustomerFK().getStreetName());
        doc.addField("HOUSE_NUMBER", carOrder.getCustomerFK().getHouseNumber());
        doc.addField("CITY", carOrder.getCustomerFK().getCity());

        template.sendBodyAndHeader("direct:SolrInsert", doc, SolrConstants.OPERATION, SolrConstants.OPERATION_INSERT);

    }
}
