package main.dao;

import main.model.Customer;

public interface CustomerDao {

    public void insertCustomer(Customer customer);

    public Customer getCustomer(int id);

    public void updateUser(Customer customer);

    public void deleteCustomer(Customer customer);
}
