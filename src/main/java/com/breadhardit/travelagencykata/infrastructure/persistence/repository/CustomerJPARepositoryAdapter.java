package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerMapper;
import com.breadhardit.travelagencykata.infrastructure.rest.dto.PutCustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerJPARepositoryAdapter implements CustomersRepository {
    private final CustomersJPARepository customersRepository;

    public CustomerJPARepositoryAdapter(CustomersJPARepository customersRepository) {
        this.customersRepository = customersRepository;
    }


    @Override
    public void saveCustomer(Customer customer) {
        CustomerEntity customerEntity = CustomerMapper.toCustomerEntity(customer);
        System.out.println(customerEntity.toString());
        if(customer != null)
            customersRepository.save(customerEntity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        Optional<CustomerEntity> customer = customersRepository.findById(id);
        return customer.map(CustomerMapper::toCustomer);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String id) {
        Optional<CustomerEntity> customer = customersRepository.findByPassportNumber(id);
        System.out.println(customer.toString());
        return customer.map(CustomerMapper::toCustomer);
    }
}
