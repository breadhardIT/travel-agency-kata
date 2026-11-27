package com.breadhardit.travelagencykata.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "CUSTOMERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CustomerEntity {
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "SURNAMES")
    private String surnames;
    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;
    @Column(name = "PASSPORT_NUMBER")
    private String passportNumber;
    @Column(name = "ENROLLMENT_DATE")
    private LocalDate enrollmentDate;
    @Column(name = "ACTIVE")
    private Boolean active;
    @Column(name = "REGISTRATION_ORDER", insertable = false, updatable = false)
    private Long registrationOrder;
}
