package main.camel.beans;

import main.model.PersonPojo;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.converter.stream.InputStreamCache;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Starting condition: CarOrder has been received
 * Result: CarOrder has been logged in the system, customer is informed
 * on order retrieval and payment is initialized
 *
 * Process: CarOrder information is extracted from the order and stored
 * in customer database. According to order information, check for financial
 * solvency is initialized or advance payment is initialized.
 */
@Component
public class SergeiBean {

    private static final Logger LOGGER = Logger.getLogger("FILE");

    @Handler
    public void process (@Body InputStreamCache inputStreamCache, Exchange exchange) throws IOException {
        exchange.setOut(exchange.getIn());

        int n = inputStreamCache.available();
        byte[] bytes = new byte[n];
        inputStreamCache.read(bytes, 0, n);
        String s = new String(bytes, StandardCharsets.UTF_8);

        exchange.getOut().setBody(s);
    }

    public void test (Exchange exchange)
    {
        exchange.setOut(exchange.getIn());
    }
}