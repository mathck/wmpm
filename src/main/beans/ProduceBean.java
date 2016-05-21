package main.beans;

import main.dao.CustomerDao;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by Mnishko Sergei on 20.05.2016.
 */
@Component
public class ProduceBean {
    private static final Logger LOGGER = Logger.getLogger(ProduceBean.class);

   // @Autowired
    CustomerDao customerDao;

    @Handler
    public String doSomething(String body) {

        //System.out.println("HUI");
        // String email = customerDao.getCustomer(1).getEmail().toString();
        //System.out.println("email: "+ email);

        return "Bye World";
    }

}
