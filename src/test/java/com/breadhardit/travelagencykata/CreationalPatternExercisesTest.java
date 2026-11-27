package com.breadhardit.travelagencykata;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreationalPatternExercisesTest {
    interface Movement {
        String id();

        long balanceChange();

        static Movement deposit(String id, long amount, String description) {
            return new Deposit(id, amount, description);
        }

        static Movement withdrawal(String id, long amount, String description) {
            return new Withdrawal(id, amount, description);
        }

        static Movement transfer(String id, long amount, String description, String destinationAccount) {
            return new Transfer(id, amount, description, destinationAccount);
        }

        static Movement annulment(String id, Movement original) {
            return new Annulment(id, original);
        }
    }

    record Deposit(String id, long amount, String description) implements Movement {
        @Override
        public long balanceChange() {
            return amount;
        }
    }

    record Withdrawal(String id, long amount, String description) implements Movement {
        @Override
        public long balanceChange() {
            return -amount;
        }
    }

    record Transfer(String id, long amount, String description, String destinationAccount) implements Movement {
        @Override
        public long balanceChange() {
            return -amount;
        }
    }

    record Annulment(String id, Movement original) implements Movement {
        @Override
        public long balanceChange() {
            return -original.balanceChange();
        }
    }

    static class Account {
        private final Map<String, Movement> movements = new HashMap<>();
        private long balance;

        void addMovement(Movement movement) {
            if (movements.putIfAbsent(movement.id(), movement) == null) {
                balance += movement.balanceChange();
            }
        }

        long balance() {
            return balance;
        }
    }

    @Test
    void appliesEachMovementAndReversesTheOriginalAmount() {
        Account account = new Account();
        account.addMovement(Movement.deposit("1", 1000, "Ingreso"));
        account.addMovement(Movement.withdrawal("2", 10, "Gastos varios"));
        assertEquals(990, account.balance());

        Movement transfer = Movement.transfer("3", 200, "Transferencia", "ES1234");
        account.addMovement(transfer);
        assertEquals(790, account.balance());

        account.addMovement(Movement.annulment("4", transfer));
        assertEquals(990, account.balance());
    }
}
