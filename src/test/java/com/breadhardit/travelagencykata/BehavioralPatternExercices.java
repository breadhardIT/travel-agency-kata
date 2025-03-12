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
    @Data
    public static class Travel {
        String id;
        String name;
        String origin;
        String destination;
        DocumentStrategy documentStrategy;

        public Travel(String id,String name,String origin,String destination) {
            this.id = id;
            this.name = name;
            this.origin = origin;
            this.destination = destination;
            this.documentStrategy = documentStrategyFactory.identifyDocumentStrategy(origin, destination);
        }
        public void scanDocuments() {
            this.documentStrategy.scanDocuments();
        }
    }

    public static class documentStrategyFactory {
        public static final List<String> SCHENGEN_COUNTRIES = List.of("Spain","France","Iceland","Italy","Portugal");
        public static DocumentStrategy identifyDocumentStrategy(String origin, String destination) {
            if(origin.equals(destination)) {
                return new sameCountryDocumentsStrategy();
            } else if(SCHENGEN_COUNTRIES.contains(origin) && SCHENGEN_COUNTRIES.contains(destination)) {
                return new schengenCountryDocumentsStrategy();
            } else {
                return new foreignCountryDocumentsStrategy();
            }
        }
    }

    public interface DocumentStrategy {
        void scanDocuments();
    }

    public static class sameCountryDocumentsStrategy implements DocumentStrategy {
        @Override
        public void scanDocuments() {
            log.info("Applying DNI...");
        }
    }

    public static class schengenCountryDocumentsStrategy implements DocumentStrategy {
        @Override
        public void scanDocuments() {
            log.info("Applying Passport");
        }
    }

    public static class foreignCountryDocumentsStrategy implements DocumentStrategy {
        @Override
        public void scanDocuments() {
            log.info("Applying visa...");
        }
    }
    @Test
    // When customer buy a new Travel we have to scan the proper documentation
    public void travelAgency() {
        List<Travel> travels = List.of(
                new Travel(UUID.randomUUID().toString(),"PYRAMIDS TOUR","Spain","EGYPT"),
                new Travel(UUID.randomUUID().toString(),"LISBOA TOUR","Spain","Portugal"),
                new Travel(UUID.randomUUID().toString(),"LISBOA TOUR","Portugal","Portugal")
        );
        for (Travel travel: travels) {
            travel.scanDocuments();
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
    public static class EmployeesRepository{
        private static final ConcurrentHashMap<String,Employee> EMPLOYEES = new ConcurrentHashMap<>();
        private static final ArrayList<NotificationObserver> NOTIFICATION_OBSERVERS = new ArrayList<>();
        public void addEmployee(Employee employee) {
            EMPLOYEES.put(employee.getId(),employee);
            activateNotificationObservers(employee);
        }

        public void addNotificationObserver(NotificationObserver notificationObserver) {
            NOTIFICATION_OBSERVERS.add(notificationObserver);
        }
        public List<Employee> getUnnotifiedEmployees() {
            return EMPLOYEES.values().stream().filter(e -> !e.greetingDone).toList();
        }

        private void activateNotificationObservers(Employee employee) {
            for(NotificationObserver notificationObserver : NOTIFICATION_OBSERVERS) {
                notificationObserver.applyNotifications(employee);
            }
        }
    }

    public interface NotificationObserver {
        void applyNotifications(Employee employee);
    }

    @Value
    @AllArgsConstructor
    public static class GreetingsNotificator implements NotificationObserver {
        EmployeesRepository employeesRepository;
        @Override
        public void applyNotifications(Employee employee) {
            log.info("Aplying notifications");
            log.info("Notifying {}", employee.getEmail());
            employee.setGreetingDone(Boolean.TRUE);
        }
    }
    @Test
    @SneakyThrows
    public void companyTest() {
        EmployeesRepository employeesRepository = new EmployeesRepository();
        GreetingsNotificator greetingsNotificator = new GreetingsNotificator(employeesRepository);
        employeesRepository.addNotificationObserver(greetingsNotificator);

        employeesRepository.addEmployee(Employee.builder().id("1").name("Pepe").email("pepe@pepemail.com").build());

        employeesRepository.addEmployee(Employee.builder().id("2").name("Juan").email("pepe@pepemail.com").build());
    }
    // Use the proper behavioral pattern to avoid the continuous querying to database
}
