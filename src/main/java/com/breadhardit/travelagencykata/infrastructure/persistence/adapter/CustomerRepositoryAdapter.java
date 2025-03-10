package com.breadhardit.travelagencykata.infrastructure.persistence.adapter;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.mapper.CustomerToCustomerEntityMapper;
import com.breadhardit.travelagencykata.infrastructure.persistence.repository.CustomersJPARepository;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@Primary
@Scope("singleton")
public class CustomerRepositoryAdapter implements CustomersRepository {

    private final CustomersJPARepository customersJPARepository;

    public CustomerRepositoryAdapter(CustomersJPARepository customersJPARepository) {
        this.customersJPARepository = customersJPARepository;
    }

    @Override
    public void saveCustomer(Customer customer) {
        CustomerEntity customerEntity = CustomerToCustomerEntityMapper.mapCustomerToCustomerEntity(customer);
        customerEntity.setEnrollmentDate(LocalDate.now());
        customerEntity.setActive(true);
        customersJPARepository.save(customerEntity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        CustomerEntity customerEntity = customersJPARepository.findById(id).orElse(null);
        return customerEntity == null ? Optional.empty()
                : Optional.of(CustomerToCustomerEntityMapper.mapCustomerEntityToCustomer(customerEntity));
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passport) {
        CustomerEntity customerEntity = customersJPARepository.findByPassportNumber(passport).orElse(null);
        return customerEntity == null ? Optional.empty()
                : Optional.of(CustomerToCustomerEntityMapper.mapCustomerEntityToCustomer(customerEntity));
    }
}
