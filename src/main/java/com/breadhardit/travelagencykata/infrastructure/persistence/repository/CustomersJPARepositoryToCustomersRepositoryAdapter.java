package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class CustomersJPARepositoryToCustomersRepositoryAdapter implements CustomersRepository {

    private final CustomersJPARepository jpaRepository;

    @Override
    public void saveCustomer(Customer customer) {
        CustomerEntity entity = mapToEntity(customer);
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return jpaRepository.findById(id)
                .map(this::mapToDomain);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passportNumber) {
        return jpaRepository.findByPassportNumber(passportNumber)
                .map(this::mapToDomain);
    }

    private CustomerEntity mapToEntity(Customer customer) {

        LocalDate enrollmentDate = LocalDate.now();

        Boolean active = customer.getActive() != null
                ? customer.getActive()
                : Boolean.FALSE;

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