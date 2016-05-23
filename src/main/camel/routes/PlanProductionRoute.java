package main.camel.routes;


import main.camel.beans.ProduceBean;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Mnishko Sergei on 20.05.2016.
 */
@Component
public class PlanProductionRoute extends RouteBuilder {

    private ProduceBean produceBean;

    @Autowired
    public PlanProductionRoute(ProduceBean produceBean) {
        this.produceBean = produceBean;
    }

    @Override
    public void configure() throws Exception {
        from("file:C:\\del\\input?noop=true")//from("direct:planProduction")
                .bean(produceBean)
//           .process(new Processor() {
//           @Override
//           public void process(Exchange exchange) throws Exception {
//               Order order = exchange.getIn().getBody(Order.class);
//               order.getOrder(id).setStatus(OrderStatus.DELIVERED);
//              }
//           })
                .delay(2000)
                .to("file:C:\\del\\output");//.to("direct:finalizeOrder");
    }
}
