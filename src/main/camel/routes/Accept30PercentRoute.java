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

    @Override
    public void configure() throws Exception {

        from("direct:accept30percent")
                .bean(Accept30PercentBean.class)
                .to("direct:queryStock");
    }
}
