package main.dao;

import main.model.Order;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
//@Component("orderDao")
public class OrderDaoImpl implements OrderDao {

    private static final Logger LOGGER = Logger.getLogger(OrderDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public Session session() {
        return sessionFactory.getCurrentSession();
    }

    public OrderDaoImpl() {

    }

    public OrderDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void insertOrder(Order order) {
        session().save(order);
    }

    @Override
    @Transactional
    public Order getOrder(int id) {
        return (Order) session().createQuery("from Order where id = :id")
                .setParameter("id", id).uniqueResult();
    }

    @Override
    @Transactional
    public void updateOrder(Order order) {
        session().update(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Order order) {
        session().delete(order);
    }
}
