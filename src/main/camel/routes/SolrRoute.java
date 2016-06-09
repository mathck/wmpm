package main.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.solr.SolrConstants;
import org.springframework.stereotype.Component;

/**
 * Created by Michael on 05.06.2016.
 */
@Component
public class SolrRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:SolrInsert")
        .setHeader(SolrConstants.OPERATION, constant(SolrConstants.OPERATION_INSERT))
        .to("solr://localhost:8983/solr/fraudCheck")
        .setHeader(SolrConstants.OPERATION, constant(SolrConstants.OPERATION_COMMIT))
        .to("solr://localhost:8983/solr/fraudCheck");

        from("timer://runOnce?repeatCount=1&delay=2000")
        .setHeader(SolrConstants.OPERATION, constant(SolrConstants.OPERATION_DELETE_BY_QUERY))
        .setBody(constant("*:*"))
        .to("solr://localhost:8983/solr/fraudCheck")
        .setHeader(SolrConstants.OPERATION, constant(SolrConstants.OPERATION_COMMIT))
        .to("solr://localhost:8983/solr/fraudCheck");

    }
}
