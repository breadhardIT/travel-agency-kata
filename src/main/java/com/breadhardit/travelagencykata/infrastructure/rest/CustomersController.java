package com.breadhardit.travelagencykata.infrastructure.rest;

import com.breadhardit.travelagencykata.application.command.command.CreateCustomerCommand;
import com.breadhardit.travelagencykata.application.command.query.GetCustomerQuery;
import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.infrastructure.rest.dto.GetCustomerDTO;
import com.breadhardit.travelagencykata.infrastructure.rest.dto.PutCustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomersController {

    private final CustomersRepository customersRepository;

    @PutMapping("/customers")
    public ResponseEntity<Void> putCustomer(@RequestBody PutCustomerDTO customer) {
        CreateCustomerCommand command = CreateCustomerCommand.builder()
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .customersRepository(customersRepository)
                .build();
        String id = command.handle();
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{customer-id}")
                        .buildAndExpand(id)
                        .toUri()).build();
    }

    @GetMapping("/customers/{customer-id}")
    public ResponseEntity<GetCustomerDTO> getCustomer(@PathVariable("customer-id") String customerId) {
        return GetCustomerQuery.builder()
                .customersRepository(customersRepository)
                .id(customerId)
                .build()
                .handle()
                .map(GetCustomerDTO::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/customers")
    public ResponseEntity<List<GetCustomerDTO>> getCustomers(
            @RequestParam("passport-number") String passportNumber) {
        return customersRepository.getCustomerByPassport(passportNumber)
                .map(GetCustomerDTO::from)
                .map(List::of)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

}
