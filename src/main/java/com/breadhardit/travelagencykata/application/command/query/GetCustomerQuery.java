package com.breadhardit.travelagencykata.application.command.query;

import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import lombok.Builder;
import lombok.Value;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Value
@Builder
public class GetCustomerQuery {
    @Builder.Default
    String passport = "";
    @Builder.Default
    String id = "";
    CustomersRepository customersRepository;
    public Optional<Customer> handle() {
        if (!StringUtils.hasText(passport)) {
            return customersRepository.getCustomerById(id);
        }
        if (!StringUtils.hasText(id)) {
            return customersRepository.getCustomerByPassport(id);
        }
        return Optional.empty();
    }

}
