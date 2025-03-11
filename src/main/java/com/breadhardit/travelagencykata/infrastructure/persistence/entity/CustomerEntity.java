package com.breadhardit.travelagencykata.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "CUSTOMERS")
@Data
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

    // Constructor sin parámetros (requerido por JPA)
    public CustomerEntity() {
    }

    // Constructor con parámetros, útil para crear entidades con el patrón Builder
    public CustomerEntity(String id, String name, String surnames, LocalDate birthDate, String passportNumber,
            LocalDate enrollmentDate, Boolean active) {
        this.id = id;
        this.name = name;
        this.surnames = surnames;
        this.birthDate = birthDate;
        this.passportNumber = passportNumber;
        this.enrollmentDate = enrollmentDate;
        this.active = active;
    }

    @PrePersist
    public void prePersist() {
        if (enrollmentDate == null) {
            enrollmentDate = LocalDate.now();
        }
        if (active == null) {
            active = false;
        }
    }
}
