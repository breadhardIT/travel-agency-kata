package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


@Repository
@Scope("singleton")
@Primary
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomersRepository{

    private final CustomersJPARepository jpaRepository;

    public void saveCustomer(Customer customer){
        jpaRepository.save(fromCustomerEntityToCustomer(customer));
    }

    public Optional<Customer> getCustomerById(String id) {
        CustomerEntity entity = jpaRepository.findById(id).orElse(null);
        return entity != null ? Optional.of(fromCustomerToCustomerEntity(entity)) : Optional.empty();
    }
    
    public Optional<Customer> getCustomerByPassport(String passport) {
        CustomerEntity entity = jpaRepository.findByPassportNumber(passport).orElse(null);
        return entity != null ? Optional.of(fromCustomerToCustomerEntity(entity)) : Optional.empty();
    }
    
    private CustomerEntity fromCustomerEntityToCustomer(Customer customer){
        return CustomerEntity.builder()
            .id(customer.getId())
            .name(customer.getName())
            .surnames(customer.getSurnames())
            .birthDate(customer.getBirthDate())
            .passportNumber(customer.getPassportNumber())
            .enrollmentDate(customer.getEnrollmentDate())
            .active(customer.getActive())
            .build();
    }
    private Customer fromCustomerToCustomerEntity(CustomerEntity customerEntity){
        return Customer.builder()
            .id(customerEntity.getId())
            .name(customerEntity.getName())
            .surnames(customerEntity.getSurnames())
            .birthDate(customerEntity.getBirthDate())
            .passportNumber(customerEntity.getPassportNumber())
            .enrollmentDate(customerEntity.getEnrollmentDate())
            .active(customerEntity.getActive())
            .build();
    }
}
