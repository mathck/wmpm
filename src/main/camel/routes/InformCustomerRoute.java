package main.camel.routes;

import main.camel.beans.InformCustomerBean;
import main.camel.beans.ProcessOrderBean;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InformCustomerRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(InformCustomerRoute.class);

    private InformCustomerBean informCustomerBean;

    @Autowired
    public InformCustomerRoute(InformCustomerBean informCustomerBean) {
        this.informCustomerBean = informCustomerBean;
    }

    @Override
    public void configure() throws Exception {

        LOGGER.info("taking route: InformCustomer -> Mail");

        from("direct:informCustomer")
            .bean(informCustomerBean);
            // todo send mail
    }
}
