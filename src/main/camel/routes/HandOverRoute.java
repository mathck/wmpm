package main.camel.routes;

import main.camel.beans.TwitterBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.twitter.TwitterComponent;
import org.springframework.stereotype.Component;

@Component
public class HandOverRoute extends RouteBuilder {

    private static String consumerKey = "OQfa7DHKsKNQljob3AvSMS4kW";
    private static String consumerSecret = "jeElO4So3aFh9djX57wJvFCkgXjI7Lnjo7KPG03a6e4J5RBaMj";
    private static String accessToken = "734700383071830016-UbULP8mIy8f9ayFBwoGYL4Mskch3CDW";
    private static String accessTokenSecret = "tDqlwAKIjyTgvvMtVrXtEp7RrNXJJq2n6NHXo2GXlEaQi";

    @Override
    public void configure() throws Exception {

        TwitterComponent tc = getContext().getComponent("twitter", TwitterComponent.class);
        tc.setAccessToken(accessToken);
        tc.setAccessTokenSecret(accessTokenSecret);
        tc.setConsumerKey(consumerKey);
        tc.setConsumerSecret(consumerSecret);

        from("direct:handOverOrder")
            .routeId("handOverOrderRoute")
            .log(LoggingLevel.INFO,"FILE", "test ${routeId} \t\t\t|\t")
            .to("seda:informCustomer")
            .bean(TwitterBean.class)
            .log(LoggingLevel.INFO,"FILE", "${routeId} \t\t\t\t|\t Order Nr.: ${header.orderID} \t|\t Order handed over and finished!  \t|\t")
            .to("twitter://timeline/user");
    }
}