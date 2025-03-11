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
        public void scanRequirements();
    }

    public static class SameCountryTravel implements TravelStrategy{
        public void scanRequirements(){
            scanDNI();
        }
    }

    public static class SchengenSpaceTravel implements TravelStrategy{
        public void scanRequirements(){
            scanDNI();
            scanPassport();
        }
    }

    public static class VisaRequiredTravel implements TravelStrategy{
        public void scanRequirements(){
            scanDNI();
            scanVisa();
        }
    }

    public static void scanVisa() {
        log.info("Applying visa...");
    }
    public static void scanDNI() {
        log.info("Applying DNI...");
    }
    public static void scanPassport() {
        log.info("Applying Passport");
    }

    @Data
    public static class Travel{
        private String id;
        private String name;
        private String origin;
        private String destination;
        private TravelStrategy strategy;

        public Travel(String id, String name, String origin, String destination, TravelStrategy strategy) {
            this.id = id;
            this.name = name;
            this.origin = origin;
            this.destination = destination;
            this.strategy = strategy;
        }
        public void scanRequirements(){
            strategy.scanRequirements();
        }
        
    }

    public static class TravelFactory{
        public static final List<String> SCHENGEN_COUNTRIES = List.of("Spain","France","Iceland","Italy","Portugal");
        
        public static Travel createTravel(String id, String name, String origin, String destination){
            TravelStrategy travelStrategy;
            if (origin.equals(destination)) travelStrategy = new SameCountryTravel();
            else if (SCHENGEN_COUNTRIES.contains(origin) && SCHENGEN_COUNTRIES.contains(destination)) travelStrategy = new SchengenSpaceTravel();
            else travelStrategy = new VisaRequiredTravel();

            return new Travel(id, name, origin, destination, travelStrategy);
        }
    }


    @Test
    // When customer buy a new Travel we have to scan the proper documentation
    public void travelAgency() {
        List<Travel> travels = List.of(
                TravelFactory.createTravel(UUID.randomUUID().toString(),"PYRAMIDS TOUR","Spain","EGYPT"),
                TravelFactory.createTravel(UUID.randomUUID().toString(),"LISBOA TOUR","Spain","Portugal"),
                TravelFactory.createTravel(UUID.randomUUID().toString(),"LISBOA TOUR","Portugal","Portugal")
        );
        for (Travel travel: travels) {
            travel.scanRequirements();
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
        public void addEmployee(Employee employee) {
            EMPLOYEES.put(employee.getId(),employee);
            List<Employee> employeesToNotify = EMPLOYEES.values().stream().filter(e -> !e.greetingDone).toList();
            employeesToNotify.forEach(e -> {
                log.info("Notifying {}", e);
                e.setGreetingDone(Boolean.TRUE);
            });
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
    // Use the proper behavioral pattern to avoid the continuous querying to database
}
