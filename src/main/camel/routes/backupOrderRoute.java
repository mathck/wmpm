package main.camel.routes;


import main.camel.beans.FinalizeOrderBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.FileComponent;
import org.springframework.stereotype.Component;

/**
 1.	Starting condition: Production finished an order
 2.	Process: Information on finished orders is received from production planning node.
             Payment will be requested using SOAP interface of invoice department.If successful, order will be dispatched.
 3.	Result: Payment will be requested. If successful, order will be dispatched

 */
@Component
public class backupOrderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("seda:backupOrder")
                .routeId("BackupOrder")
                .convertBodyTo(String.class)
                .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t Order Nr.: ${in.header.orderID} \t|\t Saving Order as ${date:now:yyyyMMdd}-${in.header.orderID}-ID.csv --- ${body.toString()}")
                .to("file:backup/orders/?fileName=${date:now:yyyyMMdd}-${in.header.orderID}.bak&autoCreate=true");

    }
}
