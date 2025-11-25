package com.breadhardit.travelagencykata;

import com.breadhardit.travelagencykata.domain.Customer;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
        public static final List<String> SCHENGEN_COUNTRIES = List.of("Spain","France","Iceland","Italy","Portugal");
        String id;
        String name;
        String origin;
        String destination;
        Boolean sameCountryTravel = Boolean.FALSE;
        Boolean schengenSpaceTravel = Boolean.FALSE;
        Boolean visaRequiredTravel = Boolean.FALSE;
        public Travel(String id,String name,String origin,String destination) {
            if (origin.equals(destination)) this.sameCountryTravel = Boolean.TRUE;
            else if (SCHENGEN_COUNTRIES.contains(origin) && SCHENGEN_COUNTRIES.contains(destination)) this.schengenSpaceTravel = Boolean.TRUE;
            else this.visaRequiredTravel = Boolean.TRUE;
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
    @Test
    // When customer buy a new Travel we have to scan the proper documentation
    public void travelAgency() {
        List<Travel> travels = List.of(
                new Travel(UUID.randomUUID().toString(),"PYRAMIDS TOUR","Spain","EGYPT"),
                new Travel(UUID.randomUUID().toString(),"LISBOA TOUR","Spain","Portugal"),
                new Travel(UUID.randomUUID().toString(),"LISBOA TOUR","Portugal","Portugal")
        );
        for (Travel travel: travels) {
            if (travel.visaRequiredTravel) scanVisa();
            else if (travel.schengenSpaceTravel) scanPassport();
            else if (travel.sameCountryTravel) scanDNI();
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
        }
        public List<Employee> getUnnotifiedEmployees() {
            return EMPLOYEES.values().stream().filter(e -> !e.greetingDone).toList();
        }
    }
    @Value
    @AllArgsConstructor
    public static class GreetingsNotificator {
        EmployeesRepository employeesRepository;
        @SneakyThrows
        public void applyNotifications() {
            while (true) {
                log.info("Aplying notifications");
                List<Employee> employeesToNotify = employeesRepository.getUnnotifiedEmployees();
                employeesToNotify.forEach(e -> {log.info("Notifying {}", e);e.setGreetingDone(Boolean.TRUE);});
                Thread.sleep(100);
            }
        }
    }
    @Test
    @SneakyThrows
    public void companyTest() {
        EmployeesRepository employeesRepository = new EmployeesRepository();
        GreetingsNotificator greetingsNotificator = new GreetingsNotificator(employeesRepository);
        new Thread(() -> greetingsNotificator.applyNotifications()).start();
        Thread.sleep(200);
        employeesRepository.addEmployee(Employee.builder().id("1").name("Pepe").email("pepe@pepemail.com").build());
        Thread.sleep(200);
        employeesRepository.addEmployee(Employee.builder().id("2").name("Juan").email("pepe@pepemail.com").build());
    }
    // Use the proper behavioral pattern to avoid the continuous querying to database
}
