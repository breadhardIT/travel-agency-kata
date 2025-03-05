package com.breadhardit.travelagencykata;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

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
    public interface TravelStrategy {
        void scanDocument();
    }

    public static class VisaTravelStrategy implements TravelStrategy {

        @Override
        public void scanDocument() {
            log.info("Applying visa...");
        }
    }

    public static class DniTravelStrategy implements TravelStrategy {
        @Override
        public void scanDocument() {
            log.info("Applying DNI...");
        }
    }

    public static class PassportTravelStrategy implements TravelStrategy {
        @Override
        public void scanDocument() {
            log.info("Applying Passport");
        }
    }

    @Data
    public static class Travel implements TravelStrategy {
        String id;
        String name;
        String origin;
        String destination;
        TravelStrategy strategy;

        public Travel(String id, String name, String origin, String destination, TravelStrategy strategy) {
            this.id = id;
            this.name = name;
            this.origin = origin;
            this.destination = destination;
            this.strategy = strategy;
        }

        @Override
        public void scanDocument() {
            this.strategy.scanDocument();
        }
    }

    public static class TravelFactory {
        public static final List<String> SCHENGEN_COUNTRIES = List.of("Spain", "France", "Iceland", "Italy", "Portugal");

        public static Travel createTravel(final String id, final String name, final String origin, final String destination) {
            return new Travel(
                    id,
                    name,
                    origin,
                    destination,
                    TravelFactory.calculateStrategy(origin, destination)
            );
        }

        //Para versiones anteriores de Java 17
        private static TravelStrategy calculateStrategyOld(final String origin, final String destination) {
            TravelStrategy strategy;
            if (origin.equals(destination)) strategy = new DniTravelStrategy();
            else if (SCHENGEN_COUNTRIES.contains(origin) && SCHENGEN_COUNTRIES.contains(destination))
                strategy = new PassportTravelStrategy();
            else strategy = new VisaTravelStrategy();
            return strategy;
        }

        //Cadena de ifs mas legible
        private static TravelStrategy calculateStrategy(final String origin, final String destination) {
            return switch (origin) {
                case String o when o.equals(destination) -> new DniTravelStrategy();
                case String o when SCHENGEN_COUNTRIES.contains(o) && SCHENGEN_COUNTRIES.contains(destination) ->
                        new PassportTravelStrategy();
                default -> new VisaTravelStrategy();
            };
        }

    }

    @Test
    // When customer buy a new Travel we have to scan the proper documentation
    public void travelAgency() {
        List<Travel> travels = List.of(
                TravelFactory.createTravel(UUID.randomUUID().toString(), "PYRAMIDS TOUR", "Spain", "EGYPT"),
                TravelFactory.createTravel(UUID.randomUUID().toString(), "LISBOA TOUR", "Spain", "Portugal"),
                TravelFactory.createTravel(UUID.randomUUID().toString(), "LISBOA TOUR", "Portugal", "Portugal")
        );
        for (Travel travel : travels) {
            travel.scanDocument();
        }
    }
    // Refactor code using the proper structural pattern


    /*
     * When a new employee is enrolled, company sends a greetins e-mail.
     * A notification service is querying the database every second looking for new employees to notify
     */
    @Builder
    @Data
    public static class Employee {
        final String id;
        final String name;
        final String email;
        @Builder.Default
        Boolean greetingDone = Boolean.FALSE;
    }

    public static class EmployeesRepository {
        private static final ConcurrentHashMap<String, Employee> EMPLOYEES = new ConcurrentHashMap<>();

        public void addEmployee(Employee employee) {
            EMPLOYEES.put(employee.getId(), employee);
            GreetingsNotificator.notify(employee);

        }
    }

    @Value
    @AllArgsConstructor
    public static class GreetingsNotificator {

        @SneakyThrows
        public static void notify(final Employee employee) {
            log.info("Aplying notifications");
            log.info("Notifying {}", employee);
        }
    }

    @Test
    @SneakyThrows
    public void companyTest() {
        EmployeesRepository employeesRepository = new EmployeesRepository();
        Thread.sleep(200);
        employeesRepository.addEmployee(Employee.builder().id("1").name("Pepe").email("pepe@pepemail.com").build());
        Thread.sleep(200);
        employeesRepository.addEmployee(Employee.builder().id("2").name("Juan").email("pepe@pepemail.com").build());
    }
}
