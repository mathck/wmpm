package main.camel.beans;

import main.model.CarOrder;
import org.apache.camel.Body;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 16.06.2016.
 */
public class OrderSplitterBean {

    public List<Message> splitOrder(@Body CarOrder body) {

        List<Message> messages = new ArrayList<Message>();
        DefaultMessage messageA = new DefaultMessage();
        DefaultMessage messageB = new DefaultMessage();
        DefaultMessage messageC = new DefaultMessage();
        DefaultMessage messageD = new DefaultMessage();

        messageA.setHeader("orderID", body.getId());
        messageB.setHeader("orderID", body.getId());
        messageC.setHeader("orderID", body.getId());
        messageD.setHeader("orderID", body.getId());

        // KSV Request
        messageA.setHeader("datasource", "KSV");
        messageA.setBody(body.getCustomerFK().getFirstName());
        messageA.setBody(messageA.getBody() + "," + body.getCustomerFK().getLastName());
        messageA.setBody(messageA.getBody() + "," + body.getCustomerFK().getStreetName());
        messageA.setBody(messageA.getBody() + "," + body.getCustomerFK().getHouseNumber());
        messageA.setBody(messageA.getBody() + "," + body.getCustomerFK().getCity());

        messages.add(messageA);

        // Schufa Request
        messageB.setHeader("datasource", "Schufa");
        messageB.setBody(body.getCustomerFK().getFirstName());
        messageB.setBody(messageB.getBody() + "," + body.getCustomerFK().getLastName());
        messageB.setBody(messageB.getBody() + "," + body.getCustomerFK().getPersonalID());

        messages.add(messageB);

        // Solr Request
        messageC.setHeader("datasource", "Solr");
        messageC.setBody(body);

        messages.add(messageC);

        // External Solvency Request
        messageD.setHeader("datasource", "ExtSol");
        messageD.setBody(body.getCustomerFK().getFirstName());
        messageD.setBody(messageD.getBody() + "," + body.getCustomerFK().getLastName());
        messageD.setBody(messageD.getBody() + "," + body.getCustomerFK().getInsuranceID());

        messages.add(messageD);

        return messages;
    }
}
