package main.camel.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.ProducerTemplate;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by Michael on 05.06.2016.
 */
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
        bodyList.add(exchange.getIn().getBody());

        ProducerTemplate template = exchange.getContext().createProducerTemplate();
        String responseXml = (String) template.requestBody("direct:SolrSelectNode1",exchange.getIn().getBody());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(responseXml.getBytes("UTF-8")));
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();

        XPathExpression expr = xpath.compile("//result");

        NodeList result = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        int numFound = Integer.valueOf(result.item(0).getAttributes().getNamedItem("numFound").getNodeValue());

        if(numFound == 0)
            responseXml = (String) template.requestBody("direct:SolrSelectNode2",exchange.getIn().getBody());

        bodyList.add(responseXml);
        exchange.getOut().setBody(responseXml);

        //logging at the end of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t|\t OrderID.: " + exchange.getOut().getHeader("orderID") + "\t\t|\t ");
    }

}
