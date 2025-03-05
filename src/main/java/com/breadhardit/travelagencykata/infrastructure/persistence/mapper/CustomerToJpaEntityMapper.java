package com.breadhardit.travelagencykata.infrastructure.persistence.mapper;

import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;

public interface CustomerToJpaEntityMapper {
    CustomerEntity mapCustomerToJpaEntity(Customer customer);
    Customer mapCustomerFromJpaEntity(CustomerEntity customer);
}
