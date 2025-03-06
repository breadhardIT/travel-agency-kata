package com.breadhardit.travelagencykata.infrastructure.persistence.repository.adapter;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.repository.CustomersJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
@Repository
@Scope("singleton")
public class DatabaseAdapter implements CustomersRepository {
    private final CustomersJPARepository customersJPARepository;

    public DatabaseAdapter(CustomersJPARepository customersJPARepository){
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
                .active(true)
                .build();
        customersJPARepository.saveAndFlush(customerEntity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        Optional<CustomerEntity> customerEntity = customersJPARepository.findById(id);
        Customer customer= null;
        if(customerEntity.isPresent()){
            customer = buildCustomerFromCustomerEntity(customerEntity.get());
        }
        return customer == null ? Optional.empty() : Optional.of(customer);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String id) {
        Optional<CustomerEntity> customerEntity = customersJPARepository.findByPassportNumber(id) ;
        Customer customer= null;
        if(customerEntity.isPresent()){
            customer = buildCustomerFromCustomerEntity(customerEntity.get());
        }
        return customer == null ? Optional.empty() : Optional.of(customer);
    }

    private Customer buildCustomerFromCustomerEntity(CustomerEntity customerEntity){
        return Customer.builder()
                .id(customerEntity.getId())
                .name(customerEntity.getName())
                .surnames(customerEntity.getSurnames())
                .birthDate(customerEntity.getBirthDate())
                .enrollmentDate(customerEntity.getEnrollmentDate())
                .active(customerEntity.getActive())
                .build();
    }
}