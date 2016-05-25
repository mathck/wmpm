package main.dao;

import main.model.Order;

public interface OrderDao {

    public void insertOrder(Order order);

    public Order getOrder(int id);

    public void updateOrder(Order order);

    public void deleteOrder(Order order);
}
