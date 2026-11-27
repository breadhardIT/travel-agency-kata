package com.breadhardit.travelagencykata;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

class BehavioralPatternExercisesTest {
    interface DocumentScanner {
        void scanIdentityDocument();

        void scanPassport();

        void scanVisa();
    }

    enum TravelDocument {
        IDENTITY_DOCUMENT(DocumentScanner::scanIdentityDocument),
        PASSPORT(DocumentScanner::scanPassport),
        VISA(DocumentScanner::scanVisa);

        private final Consumer<DocumentScanner> scan;

        TravelDocument(Consumer<DocumentScanner> scan) {
            this.scan = scan;
        }

        void scanWith(DocumentScanner scanner) {
            scan.accept(scanner);
        }
    }

    record Travel(String origin, String destination) {
        private static final Set<String> SCHENGEN_COUNTRIES = Set.of(
                "Spain", "France", "Iceland", "Italy", "Portugal");

        TravelDocument requiredDocument() {
            if (origin.equals(destination)) {
                return TravelDocument.IDENTITY_DOCUMENT;
            }
            if (SCHENGEN_COUNTRIES.contains(origin) && SCHENGEN_COUNTRIES.contains(destination)) {
                return TravelDocument.PASSPORT;
            }
            return TravelDocument.VISA;
        }

        void scanDocument(DocumentScanner scanner) {
            requiredDocument().scanWith(scanner);
        }
    }

    record Employee(String id, String name, String email) {
    }

    static class EmployeesRepository {
        private final Map<String, Employee> employees = new ConcurrentHashMap<>();
        private final List<Consumer<Employee>> enrollmentListeners = new CopyOnWriteArrayList<>();

        void addEnrollmentListener(Consumer<Employee> listener) {
            enrollmentListeners.add(listener);
        }

        void addEmployee(Employee employee) {
            if (employees.putIfAbsent(employee.id(), employee) == null) {
                enrollmentListeners.forEach(listener -> listener.accept(employee));
            }
        }
    }

    @Test
    void scansTheDocumentRequiredForEachRoute() {
        DocumentScanner scanner = mock(DocumentScanner.class);

        new Travel("Spain", "Egypt").scanDocument(scanner);
        new Travel("Spain", "Portugal").scanDocument(scanner);
        new Travel("Portugal", "Portugal").scanDocument(scanner);

        InOrder scans = inOrder(scanner);
        scans.verify(scanner).scanVisa();
        scans.verify(scanner).scanPassport();
        scans.verify(scanner).scanIdentityDocument();
    }

    @Test
    void sendsAGreetingOncePerNewEmployee() {
        EmployeesRepository repository = new EmployeesRepository();
        List<String> greeted = new ArrayList<>();
        repository.addEnrollmentListener(employee -> greeted.add(employee.email()));
        Employee pepe = new Employee("1", "Pepe", "pepe@example.com");
        Employee juan = new Employee("2", "Juan", "juan@example.com");

        repository.addEmployee(pepe);
        repository.addEmployee(juan);
        repository.addEmployee(pepe);

        assertEquals(List.of(pepe.email(), juan.email()), greeted);
    }
}
