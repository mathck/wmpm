package main.config;

import main.dao.*;
import main.model.Customer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.ConnectionFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;

@Configuration
@ComponentScan("main")
@PropertySource({"mail.properties"})
@EnableScheduling
@EnableTransactionManagement
public class SmartCarConfig extends CamelConfiguration {

    private static final Logger LOGGER = Logger
            .getLogger(SmartCarConfig.class);

    @Override
    protected CamelContext createCamelContext() throws Exception {
        return new SpringCamelContext(getApplicationContext());
    }

    @Override
    protected void setupCamelContext(CamelContext camelContext) throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://broker?broker.persistent=false");
        camelContext.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

    }

    @Bean
    public JavaMailSenderImpl mailSender(@Value("${mail.server.host}") String host,
                                         @Value("${mail.server.port}") String port,
                                         @Value("${mail.server.protocol}") String protocol,
                                         @Value("${mail.server.user.name}") String username,
                                         @Value("${mail.server.user.password}") String password) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", false);
        javaMailProperties.put("mail.smtp.quitwait", false);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.socketFactory.fallback", false);
        javaMailProperties.put("mail.debug", false);

        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        LOGGER.info("in datasource");
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false;DATABASE_TO_UPPER=false");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "create");
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return properties;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
        LOGGER.info("in sessionFactory");
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionBuilder.addProperties(getHibernateProperties());
        sessionBuilder.addAnnotatedClasses(Customer.class);
        sessionBuilder.scanPackages("main.model");
        return sessionBuilder.buildSessionFactory();
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(
            SessionFactory sessionFactory) {
        LOGGER.info("in transactionManager");
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(
                sessionFactory);

        return transactionManager;
    }

    @Autowired
    @Bean(name = "customerDao")
    public CustomerDao getCustomerDao(SessionFactory sessionFactory) {
        LOGGER.info("in customerDao bean");
        return new CustomerDaoImpl(sessionFactory);
    }

    @Autowired

    @Bean(name = "carOrderDao")
    public CarOrderDao getOrderDao(SessionFactory sessionFactory) {
        LOGGER.info("in carOrderDao bean");
        return new CarOrderDaoImpl(sessionFactory);
    }

    @Autowired
    @Bean(name = "stockDao")
    public StockDao getStockDao(SessionFactory sessionFactory) {
        LOGGER.info("in stockDao bean");
        return new StockDaoImpl(sessionFactory);
    }


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
