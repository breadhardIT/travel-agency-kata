package com.breadhardit.travelagencykata.infrastructure.adapter;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.repository.CustomersJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Primary
public class CustomersRepositoryToCustomersJPARepositoryAdapter implements CustomersRepository{
    private final CustomersJPARepository customersJPARepository;

    @Override
    public void saveCustomer(Customer customer) {
        CustomerEntity adaptedCustomerEntity = CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .enrollmentDate(LocalDate.now())
                .active(Boolean.TRUE)
                .build();
        customersJPARepository.saveAndFlush(adaptedCustomerEntity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        Customer adaptedCustomer = null;
        List<CustomerEntity> listedCustomersEntitiesInJPA = customersJPARepository.findAll();
        for (CustomerEntity customerEntity : listedCustomersEntitiesInJPA){
            if (id.equals(customerEntity.getId())){
                adaptedCustomer = Customer.builder()
                        .id(customerEntity.getId())
                        .name(customerEntity.getName())
                        .surnames(customerEntity.getSurnames())
                        .birthDate(customerEntity.getBirthDate())
                        .passportNumber(customerEntity.getPassportNumber())
                        .enrollmentDate(LocalDate.now())
                        .active(customerEntity.getActive())
                        .build();
            }
        }
        return adaptedCustomer == null? Optional.empty(): Optional.of(adaptedCustomer);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String id) {
        Customer adaptedCustomer = null;
        List<CustomerEntity> listedCustomerEntitiesInJPA = customersJPARepository.findAll();
        for (CustomerEntity customerEntity : listedCustomerEntitiesInJPA){
            if (id.equals(customerEntity.getPassportNumber())){
                adaptedCustomer = Customer.builder()
                        .id(customerEntity.getId())
                        .name(customerEntity.getName())
                        .surnames(customerEntity.getSurnames())
                        .birthDate(customerEntity.getBirthDate())
                        .passportNumber(customerEntity.getPassportNumber())
                        .enrollmentDate(LocalDate.now())
                        .active(customerEntity.getActive())
                        .build();
            }
        } return adaptedCustomer == null? Optional.empty(): Optional.of(adaptedCustomer);
    }
}
