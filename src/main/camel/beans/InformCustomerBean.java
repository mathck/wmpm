package main.camel.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Starting condition: Any component sends a message to the processor
 * consisting of message body and receiver.
 *
 * Result: Consumer will receive an email consisting of the relevant mail.
 * Process: Extract information on message body and recipient from message.
 * Retrieve mail address from user database. Create and dispatch mail using SMTP.
 */
@Component
public class InformCustomerBean {

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Autowired
    private JavaMailSender mailSender;


    private SimpleMailMessage confirmationMail = new SimpleMailMessage();

    @Handler
    public void process(Exchange exchange)
    {
        //logging at the beginning of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t|\t Order Nr.: " + exchange.getIn().getHeader("orderID"));

        int orderId = (int) exchange.getIn().getHeader("orderID");

        /*!!CarOrder order = carOrderDao.getOrder(orderId);
        Customer customer = order.getCustomerFK();

        this.confirmationMail.setTo(customer.getEmail());
        this.confirmationMail.setSubject(order.getId() + " - " + order.getStatus().name());
        this.confirmationMail.setText("We are pleased to inform you about your car\n\n" +
        order.toString() + "\n\n" +
        "Smart Car Company GmbH");*/

        this.mailSender.send(this.confirmationMail);

        //logging at the end of a process
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t|\t Order Nr.: " + exchange.getIn().getHeader("orderID") + " \t|\t E-Mail sent ...");
        LOGGER.info(this.getClass().getName().substring(17) + "\t\t\t|\t Order Nr.: " + exchange.getIn().getHeader("orderID") + "\t|\t E-Mail to " + /*!!customer.getEmail() +*/ " for orderID = " + exchange.getIn().getHeader("orderID"));

    }
}
