package main.dao;

import main.model.Stock;

/**
 * Created by maland on 24.05.2016.
 */
public interface StockDao {

    public void insertStock(Stock stock);

    public Stock getStock(int id);

    public void updateStock(Stock stock);

    public void deleteStock(Stock stock);
}
