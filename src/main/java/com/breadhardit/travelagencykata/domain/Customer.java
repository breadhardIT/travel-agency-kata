package com.breadhardit.travelagencykata.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class Customer {
    String id;
    String name;
    String surnames;
    LocalDate birthDate;
    String passportNumber;
    LocalDate enrollmentDate;
    Boolean active;
}
