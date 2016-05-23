//package main.camel.routes;
//
//import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.component.twitter.TwitterComponent;
//import org.apache.camel.component.websocket.WebsocketComponent;
///**
// * Created by Mnishko Sergei on 23.05.2016.
// */
//public class TwitterWebSocketRoute extends RouteBuilder {
//    private int port = 9090;
//    private String searchTerm;
//    private int delay = 2;
//    private String consumerKey;
//    private String consumerSecret;
//    private String accessToken;
//    private String accessTokenSecret;
//
//    public String getAccessToken() {
//        return accessToken;
//    }
//
//    public void setAccessToken(String accessToken) {
//        this.accessToken = accessToken;
//    }
//
//    public String getAccessTokenSecret() {
//        return accessTokenSecret;
//    }
//
//    public void setAccessTokenSecret(String accessTokenSecret) {
//        this.accessTokenSecret = accessTokenSecret;
//    }
//
//    public String getConsumerKey() {
//        return consumerKey;
//    }
//
//    public void setConsumerKey(String consumerKey) {
//        this.consumerKey = consumerKey;
//    }
//
//    public String getConsumerSecret() {
//        return consumerSecret;
//    }
//
//    public void setConsumerSecret(String consumerSecret) {
//        this.consumerSecret = consumerSecret;
//    }
//
//    public int getDelay() {
//        return delay;
//    }
//
//    public void setDelay(int delay) {
//        this.delay = delay;
//    }
//
//    public String getSearchTerm() {
//        return searchTerm;
//    }
//
//    public void setSearchTerm(String searchTerm) {
//        this.searchTerm = searchTerm;
//    }
//
//    public int getPort() {
//        return port;
//    }
//
//    public void setPort(int port) {
//        this.port = port;
//    }
//
//    @Override
//    public void configure() throws Exception {
//        // setup Camel web-socket component on the port we have defined
//        WebsocketComponent wc = getContext().getComponent("websocket", WebsocketComponent.class);
//        wc.setPort(port);
//        // we can serve static resources from the classpath: or file: system
//        wc.setStaticResources("classpath:.");
//
//        // setup Twitter component
//        TwitterComponent tc = getContext().getComponent("twitter", TwitterComponent.class);
//        tc.setAccessToken(accessToken);
//        tc.setAccessTokenSecret(accessTokenSecret);
//        tc.setConsumerKey(consumerKey);
//        tc.setConsumerSecret(consumerSecret);
//
//        // poll twitter search for new tweets
//        fromF("twitter://search?type=polling&delay=%s&keywords=%s", delay, searchTerm)
//                .to("log:tweet")
//                // and push tweets to all web socket subscribers on camel-tweet
//                .to("websocket:camel-tweet?sendToAll=true");
//    }
//}
