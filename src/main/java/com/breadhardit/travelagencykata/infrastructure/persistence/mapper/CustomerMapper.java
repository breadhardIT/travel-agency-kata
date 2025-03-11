package com.breadhardit.travelagencykata.infrastructure.persistence.mapper;

import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CustomerMapper {
    public Customer fromCustomerEntityToCustomer(CustomerEntity customerEntity) {
        Customer customer = null;
        if(customerEntity != null) {
            customer = Customer.builder()
                    .id(customerEntity.getId())
                    .name(customerEntity.getName())
                    .surnames(customerEntity.getSurnames())
                    .birthDate(customerEntity.getBirthDate())
                    .passportNumber(customerEntity.getPassportNumber())
                    .enrollmentDate(customerEntity.getEnrollmentDate())
                    .active(customerEntity.getActive())
                    .build();
        }

        return customer;
    }

    public CustomerEntity fromCustomerToCustomerEntity(Customer customer) {

        CustomerEntity customerEntity;
        LocalDate enrollmentDate = customer.getEnrollmentDate();
        Boolean active = customer.getActive();

        if (enrollmentDate == null) {
            enrollmentDate = LocalDate.now();
        }

        if (active == null) {
            active = false;
        }

        customerEntity = CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .enrollmentDate(enrollmentDate)
                .active(active)
                .build();
        return customerEntity;
    }

}
