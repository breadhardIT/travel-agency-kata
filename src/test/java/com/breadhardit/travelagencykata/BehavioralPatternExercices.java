package com.breadhardit.travelagencykata;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class BehavioralPatternExercices {
    /* EXERCISE 1
        Travels has an origin and a destination. Travels have some restrictions:
        - Travels with origin and destination in the same country require only Identity Document
        - Travels with origin and destination in schengen space, requires Passport
        - Travels with origin or destination out of schengen space, requires Visa
     */
    @SuperBuilder
    public static class TravelDniCountry extends Travel {
        @Override
        public void scan() {
            log.info("Applying DNI...");
        }

    }

    @SuperBuilder
    public static class TravelPassportCountry extends Travel {
        @Override
        public void scan() {
            log.info("Applying Passport");
        }

    }

    @SuperBuilder
    public static class TravelVisaRequiredCountry extends Travel {
        @Override
        public void scan() {
            log.info("Applying visa...");
        }
    }

    @Data
    @SuperBuilder
    public static abstract class Travel {
        @Builder.Default
        String id = UUID.randomUUID().toString();
        String name;
        String origin;
        String destination;

        public abstract void scan();
    }

    @Test
    // When customer buy a new Travel we have to scan the proper documentation
    public void travelAgency() {
        TravelFactory factory = new TravelFactory();
        List<Travel> travels = List.of(
                factory.buildTravel(UUID.randomUUID().toString(), "PYRAMIDS TOUR", "Spain", "EGYPT"),
                factory.buildTravel(UUID.randomUUID().toString(), "LISBOA TOUR", "Spain", "Portugal"),
                factory.buildTravel(UUID.randomUUID().toString(), "LISBOA TOUR", "Portugal", "Portugal")
        );
        for (Travel travel : travels) {
            travel.scan();
        }
    }

    @NoArgsConstructor
    public static class TravelFactory {
        public static final List<String> SCHENGEN_COUNTRIES = List.of("Spain", "France", "Iceland", "Italy", "Portugal");

        public Travel buildTravel(String id, String name, String origin, String destination) {
            if (origin.equals(destination))
                return TravelDniCountry.builder().id(id).name(name).origin(origin).destination(destination).build();
            else if (SCHENGEN_COUNTRIES.contains(origin) && SCHENGEN_COUNTRIES.contains(destination))
                return TravelPassportCountry.builder().id(id).name(name).origin(origin).destination(destination).build();
            else
                return TravelVisaRequiredCountry.builder().id(id).name(name).origin(origin).destination(destination).build();
        }
    }
    // Refactor code using the proper structural pattern


    /*
     * When a new employee is enrolled, company sends a greetins e-mail.
     * A notification service is querying the database every second looking for new employees to notify
     */

    // Interfaz Observer
    public interface Observer {
        void update(Employee employee);
    }

    // Clase Employee (Modelo)
    @Builder
    @Data
    public static class Employee {
        final String id;
        final String name;
        final String email;
    }

    public static class EmployeesRepository {
        private static final ConcurrentHashMap<String, Employee> EMPLOYEES = new ConcurrentHashMap<>();
        private final List<Observer> observers = new ArrayList<>();

        public void addEmployee(Employee employee) {
            EMPLOYEES.put(employee.getId(), employee);
            notifyObservers(employee);
        }

        public void addObserver(Observer observer) {
            observers.add(observer);
        }

        private void notifyObservers(Employee employee) {
            observers.forEach(observer -> observer.update(employee));
        }
    }

    @Value
    @AllArgsConstructor
    public static class GreetingsNotificator implements Observer {
        EmployeesRepository employeesRepository;

        @Override
        public void update(Employee employee) {
            log.info("Notifying {}", employee);
        }
    }

    @Test
    public void companyTest() throws InterruptedException {
        EmployeesRepository employeesRepository = new EmployeesRepository();
        GreetingsNotificator greetingsNotificator = new GreetingsNotificator(employeesRepository);

        employeesRepository.addObserver(greetingsNotificator);

        employeesRepository.addEmployee(Employee.builder().id("1").name("Pepe").email("pepe@pepemail.com").build());
        Thread.sleep(200);
        employeesRepository.addEmployee(Employee.builder().id("2").name("Juan").email("juan@pepemail.com").build());
    }
}
// Use the proper behavioral pattern to avoid the continuous querying to database

