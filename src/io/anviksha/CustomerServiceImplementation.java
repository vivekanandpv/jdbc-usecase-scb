package io.anviksha;

import java.sql.SQLException;
import java.util.List;

public class CustomerServiceImplementation implements CustomerService {
    @Override
    public void create(Customer customer) {
        try {
            CustomerRepository.create(customer);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> getAll() {
        try {
            return CustomerRepository.getAll();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer findById(int id) {
        try {
            return CustomerRepository.getById(id);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            CustomerRepository.deleteById(id);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
