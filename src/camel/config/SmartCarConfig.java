package camel.config;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by Polina on 11/05/2016.
 */
public class SmartCarConfig extends CamelConfiguration {

    private static final Logger LOGGER = Logger
            .getLogger(SmartCarConfig.class);

    @Override
    protected CamelContext createCamelContext() throws Exception {
        return new SpringCamelContext(getApplicationContext());
    }

    @Override
    protected void setupCamelContext(CamelContext camelContext) throws Exception {

    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
