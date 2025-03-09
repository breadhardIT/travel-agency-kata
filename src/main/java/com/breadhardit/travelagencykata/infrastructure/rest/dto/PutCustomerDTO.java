package com.breadhardit.travelagencykata.infrastructure.rest.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
@Builder
public class PutCustomerDTO {
    String name;
    String surnames;
    LocalDate birthDate;
    String passportNumber;
    LocalDate enrollmentDate;
    Boolean active;
}
