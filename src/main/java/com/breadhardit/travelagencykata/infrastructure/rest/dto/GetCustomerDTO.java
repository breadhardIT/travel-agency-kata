package com.breadhardit.travelagencykata.infrastructure.rest.dto;

import com.breadhardit.travelagencykata.domain.Customer;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class GetCustomerDTO {
    String id;
    String name;
    String surnames;
    LocalDate birthDate;
    String passportNumber;

    public static GetCustomerDTO from(Customer customer) {
        return GetCustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .build();
    }
}
