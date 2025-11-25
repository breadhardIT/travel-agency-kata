package com.breadhardit.travelagencykata.application.command.command;

import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class CreateCustomerCommand {
    String id = UUID.randomUUID().toString();
    String name;
    String surnames;
    LocalDate birthDate;
    String passportNumber;
    CustomersRepository customersRepository;
    @SneakyThrows
    public String handle() {
        Customer customer = Customer.builder()
                .id(id)
                .name(name)
                .surnames(surnames)
                .birthDate(birthDate)
                .passportNumber(passportNumber)
                .build();
        customersRepository.saveCustomer(customer);
        return customer.getId();
    }

}
