package main.camel.routes;

import main.camel.beans.OrderElementsBean;
import main.camel.beans.TestCustomerBean;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by maland on 24.05.2016.
 */
@Component
public class OrderElementsRoute extends RouteBuilder {


    private static final Logger LOGGER = Logger.getLogger(OrderElementsRoute.class);

    private OrderElementsBean orderElementsBean;

    @Autowired
    public OrderElementsRoute(OrderElementsBean orderElementsBean) {
        this.orderElementsBean = orderElementsBean;
    }

    @Override
    public void configure() throws Exception {

        LOGGER.info("taking route: orderElements -> planProduction");

        from("direct:orderElements")
                .bean(orderElementsBean)
                .to("direct:planProduction");
    }


}
