package com.breadhardit.travelagencykata.infrastructure.persistence.adapter;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.repository.CustomersJPARepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@Primary // CustomersInMemoryRepository also implements the port; this is the one to inject
public class CustomerRepositoryAdapter implements CustomersRepository {

    private final CustomersJPARepository jpaRepository;

    public CustomerRepositoryAdapter(CustomersJPARepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void saveCustomer(Customer customer) {
        jpaRepository.save(toEntity(customer));
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passportNumber) {
        // CustomersJPARepository has no findByPassportNumber, so query by example
        // (null fields of the probe are ignored).
        CustomerEntity probe = CustomerEntity.builder()
                .passportNumber(passportNumber)
                .build();
        return jpaRepository.findAll(Example.of(probe)).stream()
                .findFirst()
                .map(this::toDomain);
    }

    private CustomerEntity toEntity(Customer customer) {
        return CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .enrollmentDate(customer.getEnrollmentDate() != null ? customer.getEnrollmentDate() : LocalDate.now())
                .active(customer.getActive() != null ? customer.getActive() : Boolean.FALSE)
                .build();
    }

    private Customer toDomain(CustomerEntity entity) {
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