package main.camel.beans;

import main.model.CarOrder;
import main.model.Customer;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

@Component
public class MyMessageConverter implements MessageConverter{

    public Customer customer;
    public CarOrder order;

    @Override
    public Message toMessage(Object o, Session session) throws JMSException, MessageConversionException {

        CarOrder messageObject = (CarOrder) o;
        order = messageObject;
        MapMessage message = session.createMapMessage();
        message.setString("message", messageObject.toString());

        return message;
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {

        MapMessage mapMessage = (MapMessage) message;
        CarOrder messageObject = order;

        return messageObject;
    }
}