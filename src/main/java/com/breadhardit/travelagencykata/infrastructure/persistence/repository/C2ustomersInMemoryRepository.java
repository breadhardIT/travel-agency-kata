package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.application.port.CustomersRepository;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.repository.CustomersJPARepository;
import lombok.Builder;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Scope("singleton")
@Builder
public class C2ustomersInMemoryRepository implements CustomersRepository {
    private final CustomersJPARepository repo;

    @Override
    public void saveCustomer(Customer customer) {
        repo.save(adaptCustomer(customer));
    }

    private CustomerEntity adaptCustomer(Customer customer){
        CustomerEntity.CustomerEntityBuilder builder = CustomerEntity.builder();
        builder.id(customer.getId());
        builder.name(customer.getName());
        builder.surnames(customer.getSurnames());
        builder.birthDate(customer.getBirthDate());
        builder.passportNumber(customer.getPassportNumber());
        builder.enrollmentDate(
                customer.getEnrollmentDate() != null ? customer.getEnrollmentDate() : java.time.LocalDate.now());
        builder.active(customer.getActive() != null ? customer.getActive() : Boolean.FALSE);
        return builder.build();
    }

    private Optional<Customer> adaptCustomerEntity(Optional<CustomerEntity> optCustomer){
        if(optCustomer.isEmpty()){
            return Optional.empty();
        }
        CustomerEntity customer = optCustomer.get();
        Customer.CustomerBuilder builder = Customer.builder();
        builder.id(customer.getId());
        builder.name(customer.getName());
        builder.surnames(customer.getSurnames());
        builder.birthDate(customer.getBirthDate());
        builder.passportNumber(customer.getPassportNumber());
        builder.enrollmentDate(customer.getEnrollmentDate());
        builder.active(customer.getActive());
        return Optional.of(builder.build());
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return adaptCustomerEntity(repo.findById(id));
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String id) {
        return adaptCustomerEntity(repo.getByPassportNumber(id));
    }
}
