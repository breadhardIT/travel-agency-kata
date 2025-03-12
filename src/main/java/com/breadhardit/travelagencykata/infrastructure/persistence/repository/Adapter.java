package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@Primary
@RequiredArgsConstructor
@Scope("singleton")
public class Adapter implements CustomersRepository {

    private final CustomersJPARepository repository;
    private final Mapper mapper;
    public void saveCustomer(Customer customer){
        CustomerEntity customerEntity= mapper.getCustomerEntity(customer);
        repository.save(customerEntity);
    }
    public Optional<Customer> getCustomerById(String id){

        Optional<CustomerEntity> customerEntity = repository.findById(id);
        return customerEntity.map(this.mapper::getCustomer);

    }
    public Optional<Customer> getCustomerByPassport(String id){
        Optional<CustomerEntity> customerEntity = repository.getCustomerByPassport(id);
        return customerEntity.map(this.mapper::getCustomer);


    }
}
