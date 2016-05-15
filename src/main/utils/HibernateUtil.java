package main.utils;

/**
 * Created by Polina on 14/05/2016.
 */

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static final Logger LOGGER = Logger.getLogger(HibernateUtil.class);

    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory()
    {
        try
        {
            if (sessionFactory == null)
            {
                LOGGER.info("in buildSessionFactory");
                Configuration configuration = new Configuration().configure();
                StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
                serviceRegistryBuilder.applySettings(configuration.getProperties());
                ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
            return sessionFactory;
        } catch (Throwable ex)
        {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    public static void shutdown()
    {
        getSessionFactory().close();
    }
}