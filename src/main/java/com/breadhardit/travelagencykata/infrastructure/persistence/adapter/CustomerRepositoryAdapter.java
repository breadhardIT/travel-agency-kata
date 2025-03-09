package com.breadhardit.travelagencykata.infrastructure.persistence.adapter;

import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.repository.CustomersJPARepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@Primary
public class CustomerRepositoryAdapter implements CustomersRepository {

    private final CustomersJPARepository jpaRepository;

    public CustomerRepositoryAdapter(CustomersJPARepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void saveCustomer(Customer customer) {
        CustomerEntity entity = toEntity(customer);
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passport) {
        return jpaRepository.findByPassportNumber(passport).map(this::toDomain);
    }

    // Conversión de Customer (dominio) a CustomerEntity (persistencia)
    private CustomerEntity toEntity(Customer customer) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.getId());
        entity.setName(customer.getName());
        entity.setPassportNumber(customer.getPassportNumber());
        // Mapear otros campos si es necesario
        return entity;
    }

    // Conversión de CustomerEntity a Customer (dominio)
    private Customer toDomain(CustomerEntity entity) {
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surnames(entity.getSurnames())
                .birthDate(entity.getBirthDate())
                .passportNumber(entity.getPassportNumber())
                .enrollmentDate(entity.getEnrollmentDate())
                .active(entity.getActive())
                .build();
    }

}




