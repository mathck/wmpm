package main.camel.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class FinalizeOrderBean {

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Handler
    public void process(Exchange exchange) throws Exception {
        //getting information about payment

        //logging at the beginning of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t|\t Order Nr.: " + exchange.getIn().getHeader("orderID"));

        Random random = new Random();
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
        exchange.getOut().setHeader("paid", random.nextBoolean());

        //logging at the end of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t|\t Order Nr.: " + exchange.getOut().getHeader("orderID") + "  \t|\t New Header: paid = " + exchange.getOut().getHeader("paid").toString());
    }
}
