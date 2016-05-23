package main.camel.beans;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Created by Mnishko Sergei on 20.05.2016.
 */
@Component
public class finalizeOrderBean {

    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(finalizeOrderBean.class);


    @Autowired
    private JavaMailSender mailSender;


    private SimpleMailMessage confirmationMail = new SimpleMailMessage();


    @Handler
    public void finalizeOrder (@Body String order)throws Exception {
        this.confirmationMail.setFrom("smart.car@bk.ru");
        this.confirmationMail.setTo("smart.car@bk.ru");//order.getCustomerFK().getEmail());
        this.confirmationMail.setSubject("Confirmation: Congratulations with the new purchase!");
//        this.confirmationMail.setText("Your car: " + order.getModel() + " "
//                                        + order.getHorsepower() + " " + order.getColor()
//                                        + " has status: " + order.getStatus());
        this.confirmationMail.setText("Sending message");
        this.mailSender.send(this.confirmationMail);

    }
}
