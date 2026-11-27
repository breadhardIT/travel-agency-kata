package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomersJpaAdapter implements CustomersRepository {
    private final CustomersJPARepository repository;

    @Override
    public void saveCustomer(Customer customer) {
        repository.save(CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .enrollmentDate(Objects.requireNonNullElseGet(customer.getEnrollmentDate(), LocalDate::now))
                .active(Objects.requireNonNullElse(customer.getActive(), false))
                .build());
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return repository.findById(id).map(this::toCustomer);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passportNumber) {
        Objects.requireNonNull(passportNumber);
        Example<CustomerEntity> example = Example.of(CustomerEntity.builder()
                .passportNumber(passportNumber)
                .build());
        return repository.findBy(example, query -> query
                        .sortBy(Sort.by(Sort.Direction.DESC, "registrationOrder"))
                        .first())
                .map(this::toCustomer);
    }

    private Customer toCustomer(CustomerEntity entity) {
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
