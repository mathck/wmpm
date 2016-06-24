package main.camel.beans;

import main.model.Stock;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class StockAggregationStrategy implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            return newExchange;
        }
        oldExchange.setOut(oldExchange.getIn());
        newExchange.setOut(newExchange.getIn());

        Stock stock = newExchange.getIn().getBody(Stock.class);
        System.out.println("Available details: " + stock.getAvaliableCount());

        // TODO move 10 and 7 to settings.properties

        int difference = stock.getAvaliableCount() - 10;

        if (difference < 0) {
            oldExchange.getOut().setHeader("enoughElements", false);
            stock.setAvaliableCount(stock.getAvaliableCount() + 7);
        } else {
            oldExchange.getOut().setHeader("enoughElements", true);
            stock.setAvaliableCount(stock.getAvaliableCount() - 10);
        }

        return oldExchange;
    }
}