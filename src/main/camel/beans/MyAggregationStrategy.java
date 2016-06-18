package main.camel.beans;

import main.model.CarOrder;
import main.model.Stock;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

/**
 * Created by Mnishko Sergei on 16.06.2016.
 */
public class MyAggregationStrategy implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            return newExchange;
        }
        oldExchange.getOut().setBody(oldExchange.getIn().getBody());
        oldExchange.getOut().setHeaders(oldExchange.getIn().getHeaders());

        CarOrder order = oldExchange.getIn().getBody(CarOrder.class);
        Stock stock = newExchange.getIn().getBody(Stock.class);

        int difference = stock.getAvaliableCount() - 10;
        if (difference<0){
            oldExchange.getOut().setHeader("enoughElements", false);
        }else {
            oldExchange.getOut().setHeader("enoughElements", true);
            stock.setAvaliableCount(difference);
        }

        return oldExchange;
    }
}