package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;

import java.time.LocalDate;
import java.util.Optional;

public class CustomerRepository_CustomerJPARepository_Adapter implements CustomersRepository {
    private final CustomersJPARepository customersJPARepository;

    public CustomerRepository_CustomerJPARepository_Adapter(CustomersJPARepository customersJPARepository) {
        this.customersJPARepository = customersJPARepository;
    }

    public void saveCustomer(Customer customer) {
        customersJPARepository.save(toEntity(customer));
    }

    public Optional<Customer> getCustomerById(String id) {
        return customersJPARepository.findById(id).map(this::toDomain);
    }

    public Optional<Customer> getCustomerByPassport(String passport) {
        return customersJPARepository.findByPassportNumber(passport).map(this::toDomain);
    }

    private Customer toDomain(CustomerEntity customerEntity) {
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

    private CustomerEntity toEntity(Customer customer) {
        LocalDate enrollmentDate = (customer.getEnrollmentDate() == null) ? (LocalDate.now()) : (customer.getEnrollmentDate());
        Boolean active = (customer.getActive() == null) ? false : customer.getActive();

        return CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .enrollmentDate(enrollmentDate)
                .active(active)
                .build();
    }
}
