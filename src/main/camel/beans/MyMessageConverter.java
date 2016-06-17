package main.camel.beans;

import main.model.CarOrder;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

@Component
public class MyMessageConverter implements MessageConverter{

    @Override
    public Message toMessage(Object o, Session session) throws JMSException, MessageConversionException {

        CarOrder messageObject = (CarOrder) o;
        MapMessage message = session.createMapMessage();
        message.setString("mailId", "hui");
        message.setString("message", messageObject.toString());

        return message;
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {

        MapMessage mapMessage = (MapMessage) message;
        CarOrder messageObject = new CarOrder();
        messageObject.setMailId(mapMessage.getString("mailId"));
        messageObject.setMessage(mapMessage.getString("message"));

        return messageObject;
    }
}