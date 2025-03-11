package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Primary
@Repository
@AllArgsConstructor
@Scope("singleton")
public class Adapter implements CustomersRepository {

    CustomersJPARepository customersJPARepository;

    @Override
    public void saveCustomer(Customer customer) {

        CustomerEntity customerEntity = CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .enrollmentDate(LocalDate.now())
                .active(true)
                .build();
        customersJPARepository.save(customerEntity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        CustomerEntity customerEntity = customersJPARepository.findById(id).orElse(null);
        if(customerEntity == null) {
            return Optional.empty();
        }else{
            Customer customer = Customer.builder()
                    .id(customerEntity.getId())
                    .name(customerEntity.getName())
                    .surnames(customerEntity.getSurnames())
                    .birthDate(customerEntity.getBirthDate())
                    .passportNumber(customerEntity.getPassportNumber())
                    .build();
            return Optional.of(customer);
        }

    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passport) {
        CustomerEntity customerEntity = customersJPARepository.findByPassportNumber(passport).orElse(null);
        if(customerEntity == null) {
            return Optional.empty();
        }else{
            Customer customer = Customer.builder()
                    .id(customerEntity.getId())
                    .name(customerEntity.getName())
                    .surnames(customerEntity.getSurnames())
                    .birthDate(customerEntity.getBirthDate())
                    .passportNumber(customerEntity.getPassportNumber())
                    .build();
            return Optional.of(customer);
        }
    }
}
