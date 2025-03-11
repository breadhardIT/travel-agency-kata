package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.context.annotation.Primary;

import java.util.Optional;

@Primary

public class CustomersRepositoryAdaptador implements CustomersRepository {
    private final CustomersJPARepository customersJPARepository;

    public CustomersRepositoryAdaptador(CustomersJPARepository customersJPARepository) {
        this.customersJPARepository = customersJPARepository;
    }

    @Override
    public void saveCustomer(Customer customer) {
        customersJPARepository.save(pasarEntidad(customer));
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return customersJPARepository.findById(id).map(this::pasarDominio);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String id) {
        return customersJPARepository.findById(id).map(this::pasarDominio);
    }

    private CustomerEntity pasarEntidad(Customer customer) {
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

    private Customer pasarDominio(CustomerEntity entity) {
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