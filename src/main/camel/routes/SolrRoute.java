package main.camel.routes;

import main.camel.beans.SolrSearchBean;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.solr.SolrConstants;
import org.springframework.stereotype.Component;

@Component
public class SolrRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(org.apache.solr.client.solrj.SolrServerException.class).continued(true);

        from("direct:SolrInsert")
            .routeId("SolrInsertRoute")
            .setHeader(SolrConstants.OPERATION, constant(SolrConstants.OPERATION_INSERT))
            .to("solr://localhost:8983/solr/fraudCheck","solr://localhost:8984/solr/fraudCheck")
            .setHeader(SolrConstants.OPERATION, constant(SolrConstants.OPERATION_COMMIT))
            .to("solr://localhost:8983/solr/fraudCheck","solr://localhost:8984/solr/fraudCheck");

        from("timer://runOnce?repeatCount=1&delay=2000")
            .setHeader(SolrConstants.OPERATION, constant(SolrConstants.OPERATION_DELETE_BY_QUERY))
            .setBody(constant("*:*"))
            .to("solr://localhost:8984/solr/fraudCheck","solr://localhost:8983/solr/fraudCheck")
            .setHeader(SolrConstants.OPERATION, constant(SolrConstants.OPERATION_COMMIT))
            .to("solr://localhost:8984/solr/fraudCheck","solr://localhost:8983/solr/fraudCheck");

        from("direct:SolrSelectNode1")
            .routeId("SolrSelectNode1Route")
            .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t|\t OrderID.: ${body.id} \t|\t Perform Solr Search on Node1")
            .setBody(simple("{query:\"FIRST_NAME:${body.customerFK.firstName}~0.4 AND LAST_NAME:${body.customerFK.lastName}~0.4 AND STREET_NAME:${body.customerFK.streetName}~0.8 AND CITY:${body.customerFK.city}~0.8\"}"))
            .setHeader(Exchange.HTTP_METHOD, constant("POST"))
            .setHeader("content-type",simple("application/json"))
            .to("http://localhost:8983/solr/fraudCheck_shard1_replica1/select")
            .convertBodyTo(String.class);

        from("direct:SolrSelectNode2")
            .routeId("SolrSelectNode2Route")
            .log(LoggingLevel.ERROR,"FILE", "${routeId} \t\t\t|\t OrderID.: ${body.id} \t|\t Node1 Failed! Perform Solr Search on Node2")
            .setBody(simple("{query:\"FIRST_NAME:${body.customerFK.firstName}~0.4 AND LAST_NAME:${body.customerFK.lastName}~0.4 AND STREET_NAME:${body.customerFK.streetName}~0.8 AND CITY:${body.customerFK.city}~0.8\"}"))
            .setHeader(Exchange.HTTP_METHOD, constant("POST"))
            .setHeader("content-type",simple("application/json"))
            .to("http://localhost:8984/solr/fraudCheck_shard1_replica2/select")
            .convertBodyTo(String.class);

        from("direct:Solr")
            .routeId("SolrRoute")
            .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t\t\t|\t OrderID.: ${header.orderID} \t|\t Datasource: ${header.datasource} | Begin")
            .bean(SolrSearchBean.class)
            .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t\t\t|\t OrderID.: ${header.orderID} \t|\t Datasource: ${header.datasource} | End")
            .to("direct:Consolidate");
    }
}
