package main.dao;

import main.model.Order;
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
@Component("orderDao")
public class OrderDaoImpl {

    @Autowired
    private SessionFactory sessionFactory;

    public Session session() {
        return sessionFactory.getCurrentSession();
    }

    public void insertOrder(Order order) {
        session().save(order);
    }

    public Order getOrder(int id) {
        return (Order) session().createQuery("from Order where id = :id")
                .setParameter("id", id).uniqueResult();
    }

    public void updateOrder(Order order) {
        session().update(order);
    }

    public void deleteOrder(Order order) {
        session().delete(order);
    }
}
