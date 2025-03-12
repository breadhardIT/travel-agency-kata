package com.breadhardit.travelagencykata.infrastructure.persistence;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.repository.CustomersJPARepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class AdapterPattern implements CustomersRepository {
    private final CustomersJPARepository customersJPARepository;

    public AdapterPattern(CustomersJPARepository customersJPARepository) {
        this.customersJPARepository = customersJPARepository;
    }

    @Override
    public void saveCustomer(Customer customer) {
        CustomerEntity customerEntity = CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .enrollmentDate(LocalDate.now())
                .active(Boolean.TRUE)
                .build();

        customersJPARepository.save(customerEntity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        List<CustomerEntity> customerEntities = customersJPARepository.findAll();
        Customer customer = null;

        for (CustomerEntity customerEntity : customerEntities){
            if(customerEntity.getId().equalsIgnoreCase(id)){
                customer = Customer.builder()
                    .id(customerEntity.getId())
                    .name(customerEntity.getName())
                    .surnames(customerEntity.getSurnames())
                    .birthDate(customerEntity.getBirthDate())
                    .passportNumber(customerEntity.getPassportNumber())
                    .enrollmentDate(LocalDate.now())
                    .active(Boolean.TRUE)
                    .build();
            }
        }
        return customer == null ? Optional.empty() : Optional.of(customer);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String id) {
        List<CustomerEntity> customerEntities = customersJPARepository.findAll();
        Customer customer = null;

        for (CustomerEntity customerEntity : customerEntities){
            if(customerEntity.getPassportNumber().equalsIgnoreCase(id)){
                customer = Customer.builder()
                        .id(customerEntity.getId())
                        .name(customerEntity.getName())
                        .surnames(customerEntity.getSurnames())
                        .birthDate(customerEntity.getBirthDate())
                        .passportNumber(customerEntity.getPassportNumber())
                        .enrollmentDate(LocalDate.now())
                        .active(Boolean.TRUE)
                        .build();
            }
        }
        return customer == null ? Optional.empty() : Optional.of(customer);
    }
}
