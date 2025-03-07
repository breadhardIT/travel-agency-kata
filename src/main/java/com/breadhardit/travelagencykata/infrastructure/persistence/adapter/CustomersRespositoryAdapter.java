package com.breadhardit.travelagencykata.infrastructure.persistence.adapter;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.repository.CustomersJPARepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public class CustomersRespositoryAdapter implements CustomersRepository {
    private final CustomersJPARepository customersJPARepository;

    public CustomersRespositoryAdapter(CustomersJPARepository customersJPARepository) {
        this.customersJPARepository = customersJPARepository;
    }
    @Override
    public void saveCustomer(Customer customer) {
        LocalDate enrollmentDateAux = customer.getEnrollmentDate() != null
                ? customer.getEnrollmentDate()
                : LocalDate.now();
        Boolean activeAux = customer.getActive() != null
                ? customer.getActive()
                : Boolean.FALSE;

        CustomerEntity customerEntity = CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .enrollmentDate(enrollmentDateAux)
                .active(activeAux)
                .build();
        customersJPARepository.save(customerEntity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        Optional<CustomerEntity> customerEntity = customersJPARepository.findById(id);
        Customer customer = null;
        if (customerEntity.isPresent()) {
            customer = Customer.builder()
                    .id(customerEntity.get().getId())
                    .name(customerEntity.get().getName())
                    .surnames(customerEntity.get().getSurnames())
                    .birthDate(customerEntity.get().getBirthDate())
                    .enrollmentDate(customerEntity.get().getEnrollmentDate())
                    .active(customerEntity.get().getActive())
                    .build();
        }
        if (customer == null) {
            return Optional.empty();
        } else {
            return Optional.of(customer);
        }
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passportNumber) {
        Optional<CustomerEntity> customerEntity = customersJPARepository.findByPassportNumber(passportNumber);
        Customer customer = null;
        if (customerEntity.isPresent()) {
            customer = Customer.builder()
                    .id(customerEntity.get().getId())
                    .name(customerEntity.get().getName())
                    .surnames(customerEntity.get().getSurnames())
                    .birthDate(customerEntity.get().getBirthDate())
                    .enrollmentDate(customerEntity.get().getEnrollmentDate())
                    .active(customerEntity.get().getActive())
                    .build();
        }
        if (customer == null) {
            return Optional.empty();
        } else {
            return Optional.of(customer);
        }
    }
}
