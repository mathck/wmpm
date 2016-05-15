package main.dao;

import main.model.Customer;

/**
 * Created by Polina on 15/05/2016.
 */

public interface CustomerDao {

    public void insertCustomer(Customer customer);

    public Customer getCustomer(int id);

    public void updateUser(Customer customer);

    public void deleteCustomer(Customer customer);
}
