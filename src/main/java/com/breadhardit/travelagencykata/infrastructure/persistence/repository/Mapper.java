package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.time.LocalDate;

@Component
public class Mapper {

    public CustomerEntity getCustomerEntity(Customer customer) {
        LocalDate date= customer.getEnrollmentDate();
        Boolean active = customer.getActive();
        if (customer.getEnrollmentDate() == null) {
           date=LocalDate.now();
        }

        if (customer.getActive()==null) {
            active= true;
        }

        CustomerEntity customerEntity = CustomerEntity.builder().
                id(customer.getId()).
                name(customer.getName()).
                surnames(customer.getSurnames()).
                birthDate(customer.getBirthDate()).
                passport(customer.getPassportNumber()).
                enrollmentDate(date).
                active(active).build();
        return customerEntity;
    }

    public Customer getCustomer(CustomerEntity customerEntity) {
        if (customerEntity!=null) {
            Customer customer = Customer.builder().
                    id(customerEntity.getId()).
                    name(customerEntity.getName()).
                    surnames(customerEntity.getSurnames()).
                    birthDate(customerEntity.getBirthDate()).
                    passportNumber(customerEntity.getPassport()).
                    enrollmentDate(customerEntity.getEnrollmentDate()).
                    active(customerEntity.getActive()).build();
                    return customer;
        }else return null;

        }
    }

