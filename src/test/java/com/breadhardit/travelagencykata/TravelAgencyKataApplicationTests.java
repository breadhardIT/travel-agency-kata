package com.breadhardit.travelagencykata;

import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.repository.CustomersJPARepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class TravelAgencyKataApplicationTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomersJPARepository repository;

    @Test
    void contextLoads() {
    }

    @Test
    void savesAndFindsCustomerByIdAndPassport() throws Exception {
        URI location = createCustomer("Pepe", "123");
        String path = location.getPath();
        String id = path.substring(path.lastIndexOf('/') + 1);

        CustomerEntity saved = repository.findById(id).orElseThrow();
        assertEquals("Pepe", saved.getName());
        assertEquals("Perez", saved.getSurnames());
        assertEquals(LocalDate.of(1997, 5, 12), saved.getBirthDate());
        assertEquals("123", saved.getPassportNumber());
        assertNotNull(saved.getEnrollmentDate());
        assertEquals(Boolean.FALSE, saved.getActive());

        mvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Pepe"))
                .andExpect(jsonPath("$.surnames").value("Perez"))
                .andExpect(jsonPath("$.birthDate").value("1997-05-12"))
                .andExpect(jsonPath("$.passportNumber").value("123"));

        mvc.perform(get("/customers").param("passport-number", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(id));
    }

    @Test
    void returnsNoContentForMissingCustomer() throws Exception {
        mvc.perform(get("/customers/POTATO"))
                .andExpect(status().isNoContent());
        mvc.perform(get("/customers").param("passport-number", "POTATO"))
                .andExpect(status().isNoContent());
    }

    @Test
    void returnsLatestRegistrationWhenCustomersSharePassport() throws Exception {
        URI first = createCustomer("Ana", "456");
        createCustomer("Marta", "456");

        mvc.perform(get(first))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ana"));

        mvc.perform(get("/customers").param("passport-number", "456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Marta"));
    }

    private URI createCustomer(String name, String passportNumber) throws Exception {
        String location = mvc.perform(put("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "%s",
                                  "surnames": "Perez",
                                  "birthDate": "1997-05-12",
                                  "passportNumber": "%s"
                                }
                                """.formatted(name, passportNumber)))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);
        return URI.create(location);
    }
}
