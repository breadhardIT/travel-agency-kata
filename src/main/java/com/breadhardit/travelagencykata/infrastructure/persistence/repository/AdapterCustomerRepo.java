package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;

import java.util.Optional;

public class AdapterCustomerRepo implements CustomersRepository {
    private final CustomersJPARepository customersJPARepository;

    public AdapterCustomerRepo(CustomersJPARepository customersJPARepository) {
        this.customersJPARepository = customersJPARepository;
    }

    @Override
    public void saveCustomer(Customer customer) {
        CustomerEntity entity = mapToEntity(customer);
        customersJPARepository.save(entity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return customersJPARepository.findById(id).map(this::mapToDomain);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passport) {
        return customersJPARepository.findById(passport).map(this::mapToDomain);
    }

    private CustomerEntity mapToEntity(Customer customer) {
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

    private Customer mapToDomain(CustomerEntity entity) {
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surnames(entity.getSurnames())
                .birthDate(entity.getBirthDate())
                .passportNumber(entity.getPassportNumber())
                .enrollmentDate(entity.getEnrollmentDate())
                .active(entity.getActive())
                .build();
    }
}
