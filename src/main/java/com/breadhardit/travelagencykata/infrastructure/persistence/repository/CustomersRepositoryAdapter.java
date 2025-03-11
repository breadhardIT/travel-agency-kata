package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomersRepositoryAdapter implements CustomersRepository {
    private final CustomersJPARepository jpaRepository;

    @Override
    public void saveCustomer(Customer customer) {
        // Asegurarnos de que enrollmentDate nunca sea null
        if (customer.getEnrollmentDate() == null) {
            customer = Customer.builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .surnames(customer.getSurnames())
                    .birthDate(customer.getBirthDate())
                    .passportNumber(customer.getPassportNumber())
                    .enrollmentDate(LocalDate.now())  // Asignar la fecha actual si enrollmentDate es null
                    .active(customer.getActive() != null ? customer.getActive() : true)  // Asignar un valor por defecto a active si es null
                    .build();
        } else {
            // Asegurarse de que active no es null
            customer = Customer.builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .surnames(customer.getSurnames())
                    .birthDate(customer.getBirthDate())
                    .passportNumber(customer.getPassportNumber())
                    .enrollmentDate(customer.getEnrollmentDate())
                    .active(customer.getActive() != null ? customer.getActive() : true)  // Asignar un valor por defecto a active si es null
                    .build();
        }

        jpaRepository.save(toEntity(customer));
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passport) {
        // Necesitamos agregar una consulta en CustomersJPARepository
        return jpaRepository.findByPassportNumber(passport).map(this::toDomain);
    }

    private CustomerEntity toEntity(Customer customer) {
        return CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .enrollmentDate(customer.getEnrollmentDate())  // Ya aseguramos que no es null
                .active(customer.getActive())  // Ya aseguramos que no es null
                .build();
    }

    private Customer toDomain(CustomerEntity entity) {
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surnames(entity.getSurnames())
                .birthDate(entity.getBirthDate())
                .passportNumber(entity.getPassportNumber())
                .enrollmentDate(entity.getEnrollmentDate())
                .active(entity.getActive())  // Ya aseguramos que no es null
                .build();
    }
}
