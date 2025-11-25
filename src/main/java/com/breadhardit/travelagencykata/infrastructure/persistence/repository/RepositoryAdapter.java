package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@Primary
@Scope("singleton")
public class RepositoryAdapter implements CustomersRepository {

    @Autowired
    private CustomersJPARepository repository;

    @Override
    public void saveCustomer(Customer customer) {
        CustomerEntity customerEntity = CustomerEntity.builder().
                id(customer.getId()).
                name(customer.getName()).
                surnames(customer.getSurnames()).
                birthDate(customer.getBirthDate()).
                passportNumber(customer.getPassportNumber()).
                enrollmentDate(customer.getEnrollmentDate()).
                active(customer.getActive()).
                enrollmentDate(customer.getEnrollmentDate()).build();
        repository.save(customerEntity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        Optional<CustomerEntity> resultEntity = repository.findById(id);
        return getCustomer(resultEntity);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String id) {
        Optional<CustomerEntity> resultEntity = repository.getCustomerByPassportNumber(id);
        return getCustomer(resultEntity);
    }

    private Optional<Customer> getCustomer(Optional<CustomerEntity> resultEntity) {
        Optional<Customer> result = Optional.empty();
        if (resultEntity.isPresent()){
            CustomerEntity entity = resultEntity.get();
            result = Optional.of(Customer.builder().
                    id(entity.getId()).
                    name(entity.getName()).
                    surnames(entity.getSurnames()).
                    birthDate(entity.getBirthDate()).
                    passportNumber(entity.getPassportNumber()).
                    enrollmentDate(entity.getEnrollmentDate()).
                    active(entity.getActive()).
                    build());
        }
        return result;
    }
}
