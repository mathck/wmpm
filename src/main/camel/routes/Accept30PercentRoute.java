package main.camel.routes;


import main.camel.beans.Accept30PercentBean;
import main.camel.beans.InformCustomerBean;
import main.camel.beans.TestCustomerBean;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import main.camel.beans.InformCustomerBean;
import main.camel.beans.ProcessOrderBean;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.camel.beans.ProcessOrderBean;

@Component
public class Accept30PercentRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(Accept30PercentRoute.class);

    private Accept30PercentBean accept30PercentBean;

    @Autowired
    public Accept30PercentRoute(Accept30PercentBean accept30PercentBean) {
        this.accept30PercentBean = accept30PercentBean;
    }

    @Override
    public void configure() throws Exception {

        LOGGER.info("taking route: accept30percent -> queryStock");

        from("direct:accept30percent")
                .bean(accept30PercentBean)
                .to("direct:queryStock");
    }
}
