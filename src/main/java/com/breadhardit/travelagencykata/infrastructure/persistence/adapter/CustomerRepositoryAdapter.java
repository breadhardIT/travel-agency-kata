package com.breadhardit.travelagencykata.infrastructure.persistence.adapter;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.repository.CustomersJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@Primary
@Scope("singleton")
public class CustomerRepositoryAdapter implements CustomersRepository {

    @Autowired
    private CustomersJPARepository customersJPARepository;

    @Override
    public void saveCustomer(Customer customer) {
        CustomerEntity customerEntity = CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .enrollmentDate(LocalDate.now())
                .active(Boolean.TRUE)
                .build();
        customersJPARepository.save(customerEntity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        CustomerEntity customerEntity = customersJPARepository.findById(id).orElse(null);
        return customerEntity == null ? Optional.empty()
                : Optional.of(Customer.builder()
                .id(customerEntity.getId())
                .name(customerEntity.getName())
                .surnames(customerEntity.getSurnames())
                .birthDate(customerEntity.getBirthDate())
                .passportNumber(customerEntity.getPassportNumber())
                .enrollmentDate(customerEntity.getEnrollmentDate())
                .active(customerEntity.getActive())
                .build());
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passport) {
        CustomerEntity customerEntity = customersJPARepository.findByPassportNumber(passport).orElse(null);
        return customerEntity == null ? Optional.empty()
                : Optional.of(Customer.builder()
                .id(customerEntity.getId())
                .name(customerEntity.getName())
                .surnames(customerEntity.getSurnames())
                .birthDate(customerEntity.getBirthDate())
                .passportNumber(customerEntity.getPassportNumber())
                .enrollmentDate(customerEntity.getEnrollmentDate())
                .active(customerEntity.getActive())
                .build());
    }
}
