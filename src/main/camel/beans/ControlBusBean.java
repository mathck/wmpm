package main.camel.beans;


import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.ProducerTemplate;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;


@Component
public class ControlBusBean {

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Handler
    public void process(Exchange exchange) throws Exception {
        ProducerTemplate template = exchange.getContext().createProducerTemplate();
        String statusXml = template.requestBody("controlbus:route?action=stats", null, String.class);

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.xml.sax.InputSource iSource = new org.xml.sax.InputSource();

            //sanitize XML String
            String clean = statusXml.replaceAll( "&([^;]+(?!(?:\\w|;)))", "&amp;$1" );
            iSource.setCharacterStream(new StringReader(clean));

            //Parse XML String
            Document doc = dBuilder.parse(iSource);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("routeStat");

            boolean errorDetected=false;

            for (int temp = 0; temp < nodes.getLength(); temp++) {

                Node nNode = nodes.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if((!eElement.getAttribute("state").equals("Started")) ||
                            (Integer.valueOf(eElement.getAttribute("exchangesFailed"))!=0)) {
                        errorDetected=true;

                        LOGGER.info(this.getClass().getName().substring(17) +
                                "\t\t\t\t\t|\t CONTROL BUS \t| \t ERROR detected in " + eElement.getAttribute("id"));
                        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t\t|\t CONTROL BUS \t | \t " +
                                eElement.getAttribute("id") + "\t| \t Status : " + eElement.getAttribute("state"));
                        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t\t|\t CONTROL BUS \t | \t " +
                                eElement.getAttribute("id") + "\t| \t exchangesFailed : " +
                                eElement.getAttribute("exchangesFailed"));
                        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t\t|\t CONTROL BUS \t | \t " +
                                eElement.getAttribute("id") + "\t| \t lastExchangeCompletedTimestamp : " +
                                eElement.getAttribute("lastExchangeCompletedTimestamp"));
                        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t\t|\t CONTROL BUS \t | \t " +
                                eElement.getAttribute("id") + "\t| \t lastExchangeCompletedExchangeId : " +
                                eElement.getAttribute("lastExchangeCompletedExchangeId"));
                        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t\t|\t CONTROL BUS \t | \t " +
                                eElement.getAttribute("id") + "\t| \t firstExchangeFailureTimestamp : " +
                                eElement.getAttribute("firstExchangeFailureTimestamp"));
                        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t\t|\t CONTROL BUS \t | \t " +
                                eElement.getAttribute("id") + "\t| \t firstExchangeFailureExchangeId : " +
                                eElement.getAttribute("firstExchangeFailureExchangeId"));
                    }
                }
            }
            if(!errorDetected) {
                LOGGER.info(this.getClass().getName().substring(17) +
                        "\t\t\t\t\t|\t CONTROL BUS \t| \t No problems detected.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}