package com.breadhardit.travelagencykata;

import com.breadhardit.travelagencykata.infrastructure.rest.CustomersController;
import com.breadhardit.travelagencykata.infrastructure.rest.dto.PutCustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.Objects;

@SpringBootTest
@Slf4j
@DirtiesContext
class TravelAgencyKataApplicationTests {
    @Autowired
    CustomersController customersController;
    @BeforeEach
    public void beforeEach() {

    }
    @Test
    void contextLoads() {
        log.info("Context Loaded");
    }

    @Test
    void givenAUserWhenCreatedThenOK() {
        // Creates a new customer using the controller method
        var putCustomerResponse = customersController.putCustomer(
                PutCustomerDTO.builder().name("Pepe").surnames("Perez").birthDate(LocalDate.now()).passportNumber("123").build()
        );
        // Assert the response is OK
        Assertions.assertEquals(HttpStatus.CREATED, putCustomerResponse.getStatusCode());
        // Assert response has location header
        Assertions.assertTrue(putCustomerResponse.getHeaders().containsKey(HttpHeaders.LOCATION));
        // Assert id exists in repository, first, obtaining the id from the location
        String location = Objects.requireNonNull(putCustomerResponse.getHeaders().getLocation()).getPath();
        String id = location.substring(location.lastIndexOf("/") + 1);
        // Call get method
        var getCustomerResponse = customersController.getCustomer(id);
        Assertions.assertEquals(HttpStatus.OK, getCustomerResponse.getStatusCode());
        Assertions.assertTrue(getCustomerResponse.hasBody());
        // Call get by passport method should return 200 with body
        var getCustomerByPassportResponse = customersController.getCustomers("123");
        Assertions.assertEquals(HttpStatus.OK, getCustomerResponse.getStatusCode());
        Assertions.assertTrue(getCustomerResponse.hasBody());
    }
    @Test
    void givenNonExistingUserThen404() {
        var getCustomerResponse = customersController.getCustomer("POTATO");
        Assertions.assertEquals(HttpStatus.NO_CONTENT,getCustomerResponse.getStatusCode());
    }


}
