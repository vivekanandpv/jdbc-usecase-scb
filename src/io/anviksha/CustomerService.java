package io.anviksha;

import java.util.List;

public interface CustomerService {
    void create(Customer customer);
    List<Customer> getAll();
    Customer findById(int id);
    void deleteById(int id);
}
