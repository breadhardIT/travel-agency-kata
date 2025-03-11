package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
@Scope("singleton")
@RequiredArgsConstructor
public class CustomersJPARepositoryAdapter implements CustomersRepository {
    private final CustomersJPARepository customersJPARepository;
    private final CustomerMapper customerMapper;
    @Override
    public void saveCustomer(Customer customer) {
        CustomerEntity customerEntity = this.customerMapper.fromCustomerToCustomerEntity(customer);
        this.customersJPARepository.save(customerEntity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        Optional<CustomerEntity> customer = this.customersJPARepository.findById(id);
        return customer.map(this.customerMapper::fromCustomerEntityToCustomer);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passportId) {
        Optional<CustomerEntity> customer = this.customersJPARepository.findByPassportNumber(passportId);
        return customer.map(this.customerMapper::fromCustomerEntityToCustomer);
    }
}
