package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.application.port.CustomersRepository;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Scope("singleton")
public class CustomersInMemoryRepository implements CustomersRepository {
    private final ConcurrentHashMap<String,Customer> customersById = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String,Customer> customersByPassport = new ConcurrentHashMap<>();
    @Override
    public void saveCustomer(Customer customer) {
        customersById.put(customer.getId(),customer);
        customersByPassport.put(customer.getPassportNumber(),customer);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        Customer customer = customersById.get(id);
        return customer == null ? Optional.empty() : Optional.of(customer);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String id) {
        Customer customer = customersByPassport.get(id);
        return customer == null ? Optional.empty() : Optional.of(customer);
    }
}
