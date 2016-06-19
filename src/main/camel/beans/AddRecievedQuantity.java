package main.camel.beans;

import main.model.Stock;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

/**
 * Created by Mnishko Sergei on 17.06.2016.
 */
public class AddRecievedQuantity implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        if (oldExchange == null) {
            return newExchange;
        }
        oldExchange.setOut(oldExchange.getIn());

        Stock stock = newExchange.getIn().getBody(Stock.class);
        stock.setAvaliableCount(stock.getAvaliableCount() + 10);

        return oldExchange;
    }
}