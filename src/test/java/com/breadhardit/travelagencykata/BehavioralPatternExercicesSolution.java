package com.breadhardit.travelagencykata;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class BehavioralPatternExercicesSolution {
    /* EXERCISE 1
        Travels has an origin and a destination. Travels have some restrictions:
        - Travels with origin and destination in the same country require only Identity Document
        - Travels with origin and destination in schengen space, requires Passport
        - Travels with origin or destination out of schengen space, requires Visa
     */
    @Data
    @SuperBuilder
    public static abstract class Travel {
        @Builder.Default
        String id = UUID.randomUUID().toString();
        String name;
        String origin;
        String destination;
        TravelStrategy strategy;
        Travel() {
            this.strategy = getTravelStrategy(this.origin,this.destination);
        }
    }
    public static TravelStrategy getTravelStrategy(String origin,String destination){
        return new RegionalTravel(); //ETC
    }

    public interface TravelStrategy {
        void scanDocument();
    }
    @Data
    public static class RegionalTravel implements TravelStrategy {
        @Override
        public void scanDocument() {
            log.info("Scaning DNI..");
        }
    }
    @Data
    public static class SchengenTravel implements TravelStrategy {
        @Override
        public void scanDocument () {
            log.info("Scanning passport");
        }
    }
    @Data
    public static class VisaRequiredTravel implements TravelStrategy {
        @Override
        public void scanDocument() {
            log.info("Scanning visa");
        }
    }
        // When customer buy a new Travel we have to scan the proper documentation

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
    @AllArgsConstructor
    public static class EmployeesRepository {
        private static final ConcurrentHashMap<String, Employee> EMPLOYEES = new ConcurrentHashMap<>();
        private static final List<NotificationObserver> NOTIFICATION_OBSERVERS = new ArrayList<>();
        public void addNotificationObsevers(NotificationObserver observer) {
            NOTIFICATION_OBSERVERS.add(observer);
        }
        public void addEmployee(Employee employee) {
            EMPLOYEES.put(employee.getId(), employee);
            NOTIFICATION_OBSERVERS.forEach(e -> e.notify(employee,new Notification(employee.getEmail(),"Hello there")));
        }
        public void patchEmployee(Employee employee) {EMPLOYEES.put(employee.getId(),employee);}
    }
    @Value
    public static class Notification {
        String email;
        String text;
    }
    public interface NotificationObserver {
        void notify(Employee employee, Notification greetinsNotification);
    }
    @RequiredArgsConstructor
    public static class GreetingsNotificationObserver implements NotificationObserver {
        final EmployeesRepository employeesRepository;
        @Override
        public void notify(Employee employee, Notification greetinsNotification) {
            log.info("Sending email to {} with content: {}",greetinsNotification.getEmail(),greetinsNotification.getText());
            log.info("Updating customer");
            employeesRepository.patchEmployee(employee);
        }
    }
    @Test
    @SneakyThrows
    public void companyTest() {
        EmployeesRepository employeesRepository = new EmployeesRepository();
        GreetingsNotificationObserver greetingsNotificationObserver = new GreetingsNotificationObserver(employeesRepository);
        employeesRepository.addNotificationObsevers(greetingsNotificationObserver);
        Thread.sleep(200);
        employeesRepository.addEmployee(Employee.builder().id("1").name("Pepe").email("pepe@pepemail.com").build());
        Thread.sleep(200);
        employeesRepository.addEmployee(Employee.builder().id("2").name("Juan").email("pepe@pepemail.com").build());
    }
    // Use the proper behavioral pattern to avoid the continuous querying to database
}
