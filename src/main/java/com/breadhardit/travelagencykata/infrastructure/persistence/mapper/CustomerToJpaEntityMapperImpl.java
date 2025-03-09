package com.breadhardit.travelagencykata.infrastructure.persistence.mapper;

import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerToJpaEntityMapperImpl implements CustomerToJpaEntityMapper {
    @Override
    public CustomerEntity mapCustomerToJpaEntity(final Customer customer) {
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

    @Override
    public Customer mapCustomerFromJpaEntity(final CustomerEntity customer) {
        return Customer.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .enrollmentDate(customer.getEnrollmentDate())
                .active(customer.getActive())
                .build();
    }
}
