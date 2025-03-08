package com.breadhardit.travelagencykata.infrastructure;

import com.breadhardit.travelagencykata.application.command.query.GetCustomerQuery;
import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.repository.CustomersJPARepository;
import com.breadhardit.travelagencykata.infrastructure.rest.dto.GetCustomerDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
@Service
@Scope("singleton")
public class CustomerACustomerEntityAdapter implements CustomersRepository {

    CustomersJPARepository customersJPARepository;

    public CustomerACustomerEntityAdapter(CustomersJPARepository customersJPARepository) {
        this.customersJPARepository = customersJPARepository;
    }
    @Override
    public void saveCustomer(Customer customer){
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
    public Optional<Customer> getCustomerById(String id){
        Optional<CustomerEntity> customerEntity = customersJPARepository.findById(id);
        Customer customer = null;
        if (customerEntity.isPresent()) {
            customer = Customer.builder().name(customerEntity.get().getName())
                    .surnames(customerEntity.get().getSurnames())
                    .id(customerEntity.get().getId())
                    .birthDate(customerEntity.get().getBirthDate())
                    .enrollmentDate(customerEntity.get().getEnrollmentDate())
                    .passportNumber(customerEntity.get().getPassportNumber())
                    .active(customerEntity.get().getActive()).build();

        }
        return customer == null ? Optional.empty() : Optional.of(customer);
    }
    @Override
    public Optional<Customer> getCustomerByPassport(String id){
        Optional<CustomerEntity> customerEntity = customersJPARepository.getCustomerEntityByPassportNumber(id);
        Customer customer = null;
        System.out.println(customerEntity.isPresent());
        if (customerEntity.isPresent()) {
            customer = Customer.builder().name(customerEntity.get().getName())
                    .surnames(customerEntity.get().getSurnames())
                    .id(customerEntity.get().getId())
                    .birthDate(customerEntity.get().getBirthDate())
                    .enrollmentDate(customerEntity.get().getEnrollmentDate())
                    .passportNumber(customerEntity.get().getPassportNumber())
                    .active(customerEntity.get().getActive()).build();
        }

        return customer == null ? Optional.empty() : Optional.of(customer);
    }
}

