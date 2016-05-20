package main.dao;

import main.model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Polina on 15/05/2016.
 */
@Repository
@Transactional
@Component("customerDao")
public class CustomerDaoImpl implements CustomerDao{

    @Autowired
    private SessionFactory sessionFactory;

    public Session session() {
        return sessionFactory.getCurrentSession();
    }

    public void insertCustomer(Customer customer) {
        session().save(customer);
    }

    public Customer getCustomer(int id) {
        return (Customer) session().createQuery("from Customer where id = :id")
                .setParameter("id", id).uniqueResult();
    }

    public void updateUser(Customer customer) {
        session().update(customer);
    }

    public void deleteCustomer(Customer customer) {
        session().delete(customer);
    }
}
