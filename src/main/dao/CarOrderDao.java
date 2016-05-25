package main.dao;

import main.model.CarOrder;

public interface CarOrderDao {

    public void insertOrder(CarOrder carOrder);

    public CarOrder getOrder(int id);

    public void updateOrder(CarOrder carOrder);

    public void deleteOrder(CarOrder carOrder);
}
