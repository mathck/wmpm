package main.camel.routes;


import main.camel.beans.ProduceBean;
import main.dao.CarOrderDao;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 a.	Starting conditions: Processor receives message containing the exact order.
                         All machine elements have been reserved for production prior to message dispatch.
 b.	Process: Extract order information. Schedule machine time using a FIFO approach.
             Send expected delivery date to finalize order node.
 c.	Result: Production process is scheduled. Finalize order node will be notified once production is completed

 */
@Component
public class PlanProductionRoute extends RouteBuilder {

    @Autowired
    CarOrderDao carOrderDao;

    @Override
    public void configure() throws Exception {
        from("direct:planProduction")
           .bean(ProduceBean.class)
//           .process(new Processor() {
//           @Override
//           public void process(Exchange exchange) throws Exception {
//                Integer id = Integer.parseInt(exchange.getIn().getHeader("id").toString());
//                carOrderDao.getOrder(id).setStatus(OrderStatus.ASSEMBLING);
//              }
//           })
                .delay(2000)
//                .process(new Processor() {
//                    @Override
//                    public void process(Exchange exchange) throws Exception {
//                        Integer id = Integer.parseInt(exchange.getIn().getHeader("id").toString());
//                        carOrderDao.getOrder(id).setStatus(OrderStatus.FINISHED);
//                    }
//                })
                .to("direct:finalizeOrder");
    }
}
