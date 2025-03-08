package com.breadhardit.travelagencykata.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "CUSTOMERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerEntity {
    @Id
    @Column(name = "ID")
    String id;
    @Column(name = "NAME")
    String name;
    @Column(name = "SURNAMES")
    String surnames;
    @Column(name = "BIRTH_DATE")
    LocalDate birthDate;
    @Column(name = "PASSPORT_NUMBER")
    String passportNumber;
    @Column(name = "ENROLLMENT_DATE")
    LocalDate enrollmentDate;
    @Column(name = "ACTIVE")
    Boolean active;
}
