package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.context.annotation.Primary;

import java.util.Optional;

@Primary
public class CustomerDataAccessAdapter implements CustomersRepository {

    private final CustomersJPARepository customerDataRepository;


    public CustomerDataAccessAdapter(CustomersJPARepository customersJPARepository) {
        this.customerDataRepository = customersJPARepository;
    }


    @Override
    public void saveCustomer(Customer customer) {
        customerDataRepository.save(ConvertirAEntity(customer));
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return customerDataRepository.findById(id).map(this::ConvertirADomain);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String id) {
        return customerDataRepository.findById(id).map(this::ConvertirADomain);}


    private Customer ConvertirADomain(CustomerEntity entity) {
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

    private CustomerEntity ConvertirAEntity(Customer customer) {
        return CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .enrollmentDate(customer.getEnrollmentDate())
                .active(customer.getActive())
                .build();
    }
}
