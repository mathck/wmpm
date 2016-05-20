package main.dao;

import main.model.Order;
/**
 * Created by Polina on 15/05/2016.
 */

public interface OrderDao {

    public void insertOrder(Order order);

    public Order getOrder(int id);

    public void updateOrder(Order order);

    public void deleteOrder(Order order);
}
