package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.mapper.CustomerToJpaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@Scope("singleton")
@RequiredArgsConstructor
public class CustomersJpaRepositoryAdapter implements CustomersRepository {
    private final CustomersJPARepository customersRepository;
    private final CustomerToJpaEntityMapper mapper;

    @Override
    public void saveCustomer(final Customer customer) {
        final CustomerEntity entity = this.customerSetup(
                this.mapper.mapCustomerToJpaEntity(customer)
        );
        this.customersRepository.save(entity);
    }

    @Override
    public Optional<Customer> getCustomerById(final String id) {
        final Optional<CustomerEntity> customerEntity = this.customersRepository.findById(id);
        return customerEntity.map(this.mapper::mapCustomerFromJpaEntity);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(final String id) {
        final Optional<CustomerEntity> customerEntity = this.customersRepository.findByPassportNumber(id);
        return customerEntity.map(this.mapper::mapCustomerFromJpaEntity);
    }

    private CustomerEntity customerSetup(final CustomerEntity customerEntity) {
        customerEntity.setEnrollmentDate(LocalDate.now());
        customerEntity.setActive(true);
        return customerEntity;
    }

}
