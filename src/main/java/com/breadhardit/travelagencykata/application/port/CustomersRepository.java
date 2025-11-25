package com.breadhardit.travelagencykata.application.port;

import com.breadhardit.travelagencykata.domain.Customer;

import java.util.Optional;

public interface CustomersRepository {
    void saveCustomer(Customer customer);
    Optional<Customer> getCustomerById(String id);
    Optional<Customer> getCustomerByPassport(String id);
}
