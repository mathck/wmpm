package main.camel.beans;

import main.model.CarOrder;
import org.apache.camel.Body;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultMessage;

import java.util.ArrayList;
import java.util.List;

public class OrderSplitterBean {

    public List<Message> splitOrder(@Body CarOrder body) {

        //---------------------------------------
        // Setup
        //---------------------------------------
        List<Message> allMessages = new ArrayList<Message>();
        DefaultMessage ksv = new DefaultMessage();
        DefaultMessage schufa = new DefaultMessage();
        DefaultMessage solr = new DefaultMessage();
        DefaultMessage extSol = new DefaultMessage();

        ksv.setHeader("orderID", body.getId());
        schufa.setHeader("orderID", body.getId());
        solr.setHeader("orderID", body.getId());
        extSol.setHeader("orderID", body.getId());

        //---------------------------------------
        // KSV Request
        //---------------------------------------
        ksv.setHeader("datasource", "KSV");
        ksv.setBody(body.getCustomerFK().getFirstName());
        ksv.setBody(ksv.getBody() + "," + body.getCustomerFK().getLastName());
        ksv.setBody(ksv.getBody() + "," + body.getCustomerFK().getStreetName());
        ksv.setBody(ksv.getBody() + "," + body.getCustomerFK().getHouseNumber());
        ksv.setBody(ksv.getBody() + "," + body.getCustomerFK().getCity());

        allMessages.add(ksv);

        //---------------------------------------
        // Schufa Request
        //---------------------------------------
        schufa.setHeader("datasource", "Schufa");
        schufa.setBody(body.getCustomerFK().getFirstName());
        schufa.setBody(schufa.getBody() + "," + body.getCustomerFK().getLastName());
        schufa.setBody(schufa.getBody() + "," + body.getCustomerFK().getPersonalID());

        allMessages.add(schufa);

        //---------------------------------------
        // Solr Request
        //---------------------------------------
        solr.setHeader("datasource", "Solr");
        solr.setBody(body);

        allMessages.add(solr);

        //---------------------------------------
        // External Solvency Request
        //---------------------------------------
        extSol.setHeader("datasource", "ExtSol");
        extSol.setBody(body.getCustomerFK().getFirstName());
        extSol.setBody(extSol.getBody() + "," + body.getCustomerFK().getLastName());
        extSol.setBody(extSol.getBody() + "," + body.getCustomerFK().getInsuranceID());

        allMessages.add(extSol);

        return allMessages;
    }
}