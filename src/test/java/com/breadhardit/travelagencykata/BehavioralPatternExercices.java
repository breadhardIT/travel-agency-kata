package com.breadhardit.travelagencykata;

import lombok.*;
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

    public interface TravelStrategy {
        void scanDocumentation();
    }

    static class SameCountryTravelStrategy implements TravelStrategy {

        @Override
        public void scanDocumentation() {
            log.info("Applying DNI...");
        }
    }

    static class SchengenSpaceTravelStrategy implements TravelStrategy {

        @Override
        public void scanDocumentation() {
            log.info("Applying Passport...");
        }
    }

    static class VisaRequiredTravelStrategy implements TravelStrategy {

        @Override
        public void scanDocumentation() {
            log.info("Applying visa...");
        }
    }


    @Data
    public static class Travel {
        public static final List<String> SCHENGEN_COUNTRIES = List.of("Spain", "France", "Iceland", "Italy", "Portugal");
        private TravelStrategy travelStrategy;
        String id;
        String name;
        String origin;
        String destination;

        public Travel(String id, String name, String origin, String destination, TravelStrategy travelStrategy) {
            this.id = id;
            this.name = name;
            this.origin = origin;
            this.destination = destination;
            this.travelStrategy = travelStrategy;
        }

        public void travelDocumentation() {
            travelStrategy.scanDocumentation();
        }
    }

    @Test
    // When customer buy a new Travel we have to scan the proper documentation
    public void travelAgency() {
        List<Travel> travels = List.of(
                new Travel(UUID.randomUUID().toString(), "PYRAMIDS TOUR", "Spain", "EGYPT", new SchengenSpaceTravelStrategy()),
                new Travel(UUID.randomUUID().toString(), "LISBOA TOUR", "Portugal", "Portugal", new SameCountryTravelStrategy()),
                new Travel(UUID.randomUUID().toString(), "LISBOA TOUR", "Spain", "Portugal", new SchengenSpaceTravelStrategy()),
                new Travel(UUID.randomUUID().toString(), "PYRAMIDS TOUR", "Spain", "EGYPT", new VisaRequiredTravelStrategy())
        );

        for (Travel travel : travels) {
            travel.travelDocumentation();
        }
    }
    // Refactor code using the proper structural pattern


    /*
     * When a new employee is enrolled, company sends a greetins e-mail.
     * A notification service is querying the database every second looking for new employees to notify
     */

    public interface Observer {
        void update(Employee employee);
    }

    public interface SubjectObserver {
        void addObserver(Observer observer);

        void removeObserver(Observer observer);

        void notifyObservers(Employee employee);
    }

    @Builder
    @Data
    public static class Employee {
        final String id;
        final String name;
        final String email;
    }

    @Data
    @Builder
    public static class EmployeesRepository implements SubjectObserver {
        private final ConcurrentHashMap<String, Employee> EMPLOYEES = new ConcurrentHashMap<>();
        private final ArrayList<Observer> observers = new ArrayList<>();

        public void addEmployee(Employee employee) {
            EMPLOYEES.put(employee.getId(), employee);
            notifyObservers(employee);
        }

        @Override
        public void addObserver(Observer observer) {
            observers.add(observer);
        }

        @Override
        public void removeObserver(Observer observer) {
            observers.remove(observer);
        }

        @Override
        public void notifyObservers(Employee employee) {
            for (Observer observer : observers) {
                observer.update(employee);
            }
        }
    }

    @Value
    @AllArgsConstructor
    public static class GreetingsNotificator implements Observer {
        EmployeesRepository employeesRepository;

        @Override
        public void update(Employee employee) {
            log.info("Aplying notifications");
            log.info("Notifying {}, employee ID {}", employee.getName(), employee.getId());
        }
    }

    @Test
    @SneakyThrows
    public void companyTest() {
        EmployeesRepository employeesRepository = new EmployeesRepository();
        GreetingsNotificator greetingsNotificator = new GreetingsNotificator(employeesRepository);

        employeesRepository.addObserver(greetingsNotificator);
        employeesRepository.addEmployee(Employee.builder().id("1").name("Pepe").email("pepe@pepemail.com").build());
        employeesRepository.addEmployee(Employee.builder().id("2").name("Juan").email("pepe@pepemail.com").build());


    }
    // Use the proper behavioral pattern to avoid the continuous querying to database
}
