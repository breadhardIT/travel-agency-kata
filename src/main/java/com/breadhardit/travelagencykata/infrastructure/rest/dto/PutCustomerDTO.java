package com.breadhardit.travelagencykata.infrastructure.rest.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Value
@Builder
@Jacksonized
public class PutCustomerDTO {
    String name;
    String surnames;
    LocalDate birthDate;
    String passportNumber;
    Boolean active;
}
