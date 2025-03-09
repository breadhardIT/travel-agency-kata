package com.breadhardit.travelagencykata.infrastructure.persistence.adapter;

import ch.qos.logback.classic.pattern.LineOfCallerConverter;
import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.repository.CustomersJPARepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class CustomersRepositoryAdapter implements CustomersRepository {

    private final CustomersJPARepository jpaRepository;

    public CustomersRepositoryAdapter(CustomersJPARepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void saveCustomer(Customer customer) {
        // Convert domain Customer to JPA entity and save
        CustomerEntity entity = toEntity(customer);
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        // Find CustomerEntity by ID and map it to domain Customer
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passportNumber) {
        // Find CustomerEntity by passport number and map it to domain Customer
        return jpaRepository.findByPassportNumber(passportNumber).map(this::toDomain);
    }

    /**
     * Converts a JPA CustomerEntity to a domain Customer.
     *
     * @param entity the CustomerEntity to convert
     * @return the corresponding domain Customer
     */
    private Customer toDomain(CustomerEntity entity) {
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surnames(entity.getSurnames())
                .birthDate(entity.getBirthDate())
                .passportNumber(entity.getPassportNumber())
                .build();
    }

    /**
     * Converts a domain Customer to a JPA CustomerEntity.
     *
     * @param customer the domain Customer to convert
     * @return the corresponding JPA CustomerEntity
     */
    private CustomerEntity toEntity(Customer customer) {

        LocalDate enrollmentDate = LocalDate.now();

        Boolean active = false;

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