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

        LOGGER.info("taking route: ProcessOrder -> checkFinancialSolvency");

        from("direct:processOrder")
            .bean(processOrderBean)
            .to("direct:informCustomer")
            .to("direct:checkFinancialSolvency");
    }
}
