package main.camel.beans;

import main.model.CarOrder;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.util.ArrayList;

@Component
public class ConsolidateBean {

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Handler
    public void process(Exchange exchange) throws Exception {

        ArrayList<Object> bodyList = (ArrayList<Object>) exchange.getIn().getBody();

        DocumentBuilderFactory  dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource source;

        Document doc;
        XPathFactory xpathfactory;
        XPath xpath;

        // Schufa variables
        String statusSchufa = "CRITICAL";
        int nrOfDunningsSchufa = 99;

        // KSV variables
        Boolean personFoundKSV = false;
        String personStatusKSV = "NOTFOUND";

        // ExtSol variables
        Boolean extfraudHintExtSol = true;
        int nrOfContractsExtSol = 99,deptAmountExtSol = 9999;

        // Solr variables
        int hitSolr = 1;

        // Get values with XPath
        for(int i=0; i<4; i++){
            if(bodyList.get(i) != null){
                if(bodyList.get(i) instanceof String && ((String) bodyList.get(i)).contains("ExtSolRequestResponse")) {

                    source = new InputSource(new StringReader((String) bodyList.get(i)));
                    doc = db.parse(source);
                    xpathfactory = XPathFactory.newInstance();
                    xpath = xpathfactory.newXPath();

                    extfraudHintExtSol = Boolean.valueOf(xpath.evaluate("//ExtfraudHint",doc));
                    nrOfContractsExtSol = Integer.valueOf(xpath.evaluate("//NrOfContracts",doc));
                    deptAmountExtSol = Integer.valueOf(xpath.evaluate("//DeptAmount",doc));

                }
                else if(bodyList.get(i) instanceof String && ((String) bodyList.get(i)).contains("KSVRequestResponse")) {

                    source = new InputSource(new StringReader((String) bodyList.get(i)));
                    doc = db.parse(source);
                    xpathfactory = XPathFactory.newInstance();
                    xpath = xpathfactory.newXPath();

                    personFoundKSV = Boolean.valueOf(xpath.evaluate("//personFound",doc));
                    personStatusKSV = xpath.evaluate("//personStatus",doc);

                }
                else if(bodyList.get(i) instanceof String && ((String) bodyList.get(i)).contains("SchufaRequestResponse")) {

                    source = new InputSource(new StringReader((String) bodyList.get(i)));
                    doc = db.parse(source);
                    xpathfactory = XPathFactory.newInstance();
                    xpath = xpathfactory.newXPath();

                    statusSchufa = xpath.evaluate("//SchufaStatus",doc);
                    nrOfDunningsSchufa = Integer.valueOf(xpath.evaluate("//NrOfDunnings",doc));

                }
                else if(bodyList.get(i) instanceof String && ((String) bodyList.get(i)).contains("<lst name=\"responseHeader\"><int name=\"status\">")) {
                    source = new InputSource(new StringReader((String) bodyList.get(i)));
                    doc = db.parse(source);
                    xpathfactory = XPathFactory.newInstance();
                    xpath = xpathfactory.newXPath();

                    hitSolr = Integer.valueOf(xpath.evaluate("//response/result/@numFound",doc));

                }
                else if(bodyList.get(i) instanceof CarOrder) {
                    exchange.getIn().setBody(bodyList.get(i));
                }
            }
        }
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t\t\t|\t OrderID.: " + exchange.getIn().getHeader("orderID") + "\t|\t Schufa[" + statusSchufa + ", " + nrOfDunningsSchufa + "], KSV[" + personFoundKSV + ", " + personStatusKSV + "], ExtSol[" + extfraudHintExtSol + ", " + nrOfContractsExtSol + ", " + deptAmountExtSol + "], Solr[" + hitSolr + "]");

        // Calculate solvencyApproval

        // KSV NOTFOUND + Schufa SEVERE + Solr hit
        if(!personFoundKSV && nrOfDunningsSchufa > 10 && hitSolr > 0){
            exchange.getIn().setHeader("solvencyApproval",false);
        }

        // KSV NOTFOUND + Schufa CRITICAL
        else if(!personFoundKSV && nrOfDunningsSchufa > 15){
            exchange.getIn().setHeader("solvencyApproval",false);
        }

        // Schufa SEVERE + ExtSol FRAUDHINT
        else if(nrOfDunningsSchufa > 10 && extfraudHintExtSol){
            exchange.getIn().setHeader("solvencyApproval",false);
        }

        // Schufa SEVERE + ExtSol NUMBEROFCONTRACTS > 5 + DEPTAMOUNT > 500 + Solr hit
        else if(nrOfDunningsSchufa > 10 && nrOfContractsExtSol > 5 && deptAmountExtSol > 500 && hitSolr > 0){
            exchange.getIn().setHeader("solvencyApproval",false);
        }

        // Schufa CRITICAL + KSV CRITICAL
        else if(nrOfDunningsSchufa > 15 && personStatusKSV.equals("CRITICAL")){
            exchange.getIn().setHeader("solvencyApproval",false);
        }

        // KSV CRITICAL + ExtSol FRAUDHINT
        else if(personStatusKSV.equals("CRITICAL") && extfraudHintExtSol){
            exchange.getIn().setHeader("solvencyApproval",false);
        }

        else {
            exchange.getIn().setHeader("solvencyApproval",true);
        }
    }
}