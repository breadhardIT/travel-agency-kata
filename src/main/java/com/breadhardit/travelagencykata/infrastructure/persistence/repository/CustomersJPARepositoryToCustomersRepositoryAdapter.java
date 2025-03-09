package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary // Para darle prioridad sobre la implementación InMemory
@RequiredArgsConstructor
public class JpaCustomersRepository implements CustomersRepository {

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
    public Optional<Customer> getCustomerByPassport(String passport) {
        return Optional.empty();
    }

    private CustomerEntity mapToEntity(Customer customer) {
        return CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .build();
    }

    private Customer mapToDomain(CustomerEntity entity) {
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surnames(entity.getSurnames())
                .build();
    }
}