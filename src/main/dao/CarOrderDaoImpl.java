package main.dao;

import main.model.CarOrder;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
//@Component("orderDao")
public class CarOrderDaoImpl implements CarOrderDao {

    private static final Logger LOGGER = Logger.getLogger(CarOrderDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    public Session session() {
        return sessionFactory.getCurrentSession();
    }

    public CarOrderDaoImpl() {

    }

    public CarOrderDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void insertOrder(CarOrder carOrder) {
        session().save(carOrder);
    }

    @Override
    @Transactional
    public CarOrder getOrder(int id) {
        return (CarOrder) session().createQuery("from CarOrder where id = :id")
                .setParameter("id", id).uniqueResult();
    }

    @Override
    @Transactional
    public void updateOrder(CarOrder carOrder) {
        session().update(carOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(CarOrder carOrder) {
        session().delete(carOrder);
    }
}
