package main.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.ConnectionFactory;
import java.util.Properties;

@Configuration
@ComponentScan("main")
@EnableScheduling
@EnableTransactionManagement
public class SmartCarConfig extends CamelConfiguration {

    private static final Logger LOGGER = Logger.getLogger(SmartCarConfig.class);

    @Override
    protected CamelContext createCamelContext() throws Exception {
        return new SpringCamelContext(getApplicationContext());
    }

    @Override
    protected void setupCamelContext(CamelContext camelContext) throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://broker?broker.persistent=false");
        camelContext.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        PropertiesComponent pc = new PropertiesComponent();
        pc.setLocation("myprop.properties");
        camelContext.addComponent("properties", pc);
    }
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}