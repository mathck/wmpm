package main.camel.routes;

import main.camel.beans.CreateOrderBean;
import main.camel.beans.ProcessOrderBean;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(CreateOrderRoute.class);

    private ProcessOrderBean processOrderBean;

    @Autowired
    public CreateOrderRoute(ProcessOrderBean processOrderBean) {
        this.processOrderBean = processOrderBean;
    }

    @Override
    public void configure() throws Exception {
        from("file:C:\\del\\input?noop=true")
            .bean(CreateOrderBean.class)
            .bean(CreateOrderBean.class, "createOrder")
            .to("seda: finalize.queque");
    }
}
