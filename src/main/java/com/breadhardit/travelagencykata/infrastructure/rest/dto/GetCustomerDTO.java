package com.breadhardit.travelagencykata.infrastructure.rest.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
@Builder
public class GetCustomerDTO {
    String id;
    String name;
    String surnames;
    LocalDate birthDate;
    LocalDate enrollmentDate;
    String passportNumber;
}
