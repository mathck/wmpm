package main.camel.beans;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

//simply combines Exchange body values into an ArrayList<Object>
public class ArrayListAggregationStrategy implements AggregationStrategy {

    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Object response;
        StringWriter writer;

        Object newBody = newExchange.getIn().getBody();
        ArrayList<Object> list = null;

        if( newBody instanceof InputStream) {
            writer = new StringWriter();
            try {
                IOUtils.copy((InputStream) newBody, writer, Charset.forName("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            response = writer.toString();
        } else {
            response = newBody;
        }

        if (oldExchange == null) {

            list = new ArrayList<Object>();
            if(response instanceof String) {
                list.add(response);
            } else {
                list.add(((ArrayList) response).get(0));
                list.add(((ArrayList) response).get(1));
            }
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {

            list = oldExchange.getIn().getBody(ArrayList.class);

            if(response instanceof String) {
                list.add(response);
            } else {
                list.add(((ArrayList) response).get(0));
                list.add(((ArrayList) response).get(1));
            }
            return oldExchange;
        }
    }
}
