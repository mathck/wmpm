package main.dao;

import main.model.Customer;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CustomerDaoImpl implements CustomerDao {

    private static final Logger LOGGER = Logger.getLogger(CustomerDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;


    public Session session() {
        LOGGER.info("in session");
        LOGGER.info(sessionFactory.toString());
        return sessionFactory.getCurrentSession();
    }

    public CustomerDaoImpl() {

    }

    public CustomerDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void insertCustomer(Customer customer) {
        LOGGER.info("in insertCustomer " + customer.toString());
        session().save(customer);
    }

    @Override
    @Transactional
    public Customer getCustomer(int id) {
        return (Customer) session().createQuery("from Customer where id = :id")
                .setParameter("id", id).uniqueResult();
    }

    @Override
    @Transactional
    public void updateUser(Customer customer) {
        session().update(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Customer customer) {
        session().delete(customer);
    }
}
