package main.dao;

import main.model.Stock;
import org.apache.camel.processor.LoggingErrorHandler;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
//@Component("stockDao")
public class StockDaoImpl implements StockDao {

    private static final Logger LOGGER = Logger.getLogger(CustomerDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public Session session() {
        LOGGER.info("in session");
        LOGGER.info(sessionFactory.toString());
        return sessionFactory.getCurrentSession();
    }

    public StockDaoImpl() {

    }

    public StockDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void insertStock(Stock stock) {
        LOGGER.info("in insertStock " + stock.toString());
        session().save(stock);
    }

    @Override
    @Transactional
    public Stock getStock(int id) {
        return (Stock) session().createQuery("from Stock where id = :id")
                .setParameter("id", id).uniqueResult();
    }


    @Override
    @Transactional
    public void updateStock(Stock stock) {
        session().update(stock);
    }

    @Override
    @Transactional
    public void deleteStock(Stock stock) {
        session().delete(stock);
    }
}
