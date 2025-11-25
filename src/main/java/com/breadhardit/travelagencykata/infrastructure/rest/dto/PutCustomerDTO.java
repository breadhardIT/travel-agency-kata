package com.breadhardit.travelagencykata.infrastructure.rest.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class PutCustomerDTO {
    String name;
    String surnames;
    LocalDate birthDate;
    String passportNumber;
    Boolean active;
}
