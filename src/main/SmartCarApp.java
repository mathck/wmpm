package main;

import main.config.SmartCarConfig;
import org.apache.camel.spring.Main;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class SmartCarApp extends Main {

    private static final Logger LOGGER = LoggerFactory.getLogger("FILE");


    public static void main(String... args) throws Exception {
        BasicConfigurator.configure();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SmartCarConfig.class);
        SmartCarApp smartCarApp = context.getBean(SmartCarApp.class); // new
        LOGGER.info("");
        LOGGER.info("----- Initial start of Server! -----");



        //SmartCarApp smartCarApp = new SmartCarApp();
        //the above line was bad see:
        //http://stackoverflow.com/questions/3659720/using-spring-3-autowire-in-a-standalone-java-application


        smartCarApp.setApplicationContext(context);
//        smartCarApp.run();

    }

    public void run() {
        BasicConfigurator.configure();
        super.enableHangupSupport();
        try {
            LOGGER.info("Server is running!");
            super.run();
        } catch (Exception e) {
            LOGGER.error("App can not be started, caused by " + e.getMessage(), e);
        }
    }
}
