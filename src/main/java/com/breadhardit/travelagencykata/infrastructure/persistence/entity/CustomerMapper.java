package com.breadhardit.travelagencykata.infrastructure.persistence.entity;

import com.breadhardit.travelagencykata.domain.Customer;

import java.time.LocalDate;

public class CustomerMapper {
    public static Customer toCustomer(CustomerEntity entity){
            Customer customer = null;
        if(entity != null) {
            customer = Customer.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .surnames(entity.getSurnames())
                    .birthDate(entity.getBirthDate())
                    .passportNumber(entity.getPassportNumber())
                    .enrollmentDate(entity.getEnrollmentDate())
                    .active(entity.getActive())
                    .build();
        }
        return customer;
    }

    public static CustomerEntity toCustomerEntity(Customer customer){
        CustomerEntity customerEntity = null;
        if(customer != null) {
            customerEntity = CustomerEntity.builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .surnames(customer.getSurnames())
                    .birthDate(customer.getBirthDate())
                    .passportNumber(customer.getPassportNumber())
                    .enrollmentDate(LocalDate.now())
                    .active(true)
                    .build();
        }
        return customerEntity;
    }
}
