package main.camel.routes;

import main.camel.beans.InformCustomerBean;
import main.camel.beans.ProcessOrderBean;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessOrderRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(ProcessOrderRoute.class);

    private ProcessOrderBean processOrderBean;

    private InformCustomerBean informCustomerBean;

    @Autowired
    public ProcessOrderRoute(ProcessOrderBean processOrderBean, InformCustomerBean informCustomerBean) {
        this.processOrderBean = processOrderBean;
        this.informCustomerBean = informCustomerBean;
    }

    @Override
    public void configure() throws Exception {

        LOGGER.debug("taking route: ProcessOrder -> InformCustomer");

        from("direct:start")
            .bean(informCustomerBean)
            .to("log:foo")
            .wireTap("direct:tap")
            .to("direct:orderProcess");
    }
}
