package main.dao;

import main.model.Stock;

public interface StockDao {

    public void insertStock(Stock stock);

    public Stock getStock(int id);

    public void updateStock(Stock stock);

    public void deleteStock(Stock stock);
}
