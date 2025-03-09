package com.breadhardit.travelagencykata;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.timeout;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CreationalPatternExercices {
    /*
     * Banking accounts has movements. And each movement can be a deposit or withdrawal
     * After a few months operating, we need to create new types of movements:
     *   - TRANSFER: It's a withdrawal, bet we need the destination account number
     *   - ANNULMENT: It cancels a movement, so, we need the original movement
     */

    @Data
    public static abstract class Movement {
        String id;
        String description;

        public Movement(String id, String description) {
            this.id = id;
            this.description = description;
        }

        public abstract void executeMovement(Account account);

        public abstract void undoMovement(Account account);
    }

    public static class MovementFactory {

        public static Deposit createDeposit(String id, Long amount, String description) {
            return new Deposit(id, amount, description);
        }

        public static Withdrawal createWithdrawal(String id, Long amount, String description) {
            return new Withdrawal(id, amount, description);
        }

        public static Transfer createTransfer(String id, Long amount, String description, Account destinationAccount) {
            return new Transfer(id, amount, description, destinationAccount);
        }

        public static Annulment createAnnulment(String id, String description, Movement originalMovement) {
            return new Annulment(id, description, originalMovement);
        }
    }

    public static class Deposit extends Movement {
        private final Long amount;

        public Deposit(String id, Long amount, String description) {
            super(id, description);
            this.amount = amount;
        }

        @Override
        public void executeMovement(Account account) {
            account.setBalance(account.getBalance() + amount);
            log.info("Deposited " + amount + " to account " + account.id);
        }

        @Override
        public void undoMovement(Account account) {
            account.setBalance(account.getBalance() - amount);
            log.info("Deposit cancelled " + amount + " to account " + account.id);
        }
    }

    public static class Withdrawal extends Movement {
        private final Long amount;

        public Withdrawal(String id, Long amount, String description) {
            super(id, description);
            this.amount = amount;
        }

        @Override
        public void executeMovement(Account account) {
            account.setBalance(account.getBalance() - amount);
            log.info("Withdrawal " + amount + " to account " + account.id);
        }

        @Override
        public void undoMovement(Account account) {
            account.setBalance(account.getBalance() + amount);
            log.info("Withdrawal cancelled " + amount + " to account " + account.id);
        }
    }

    public static class Transfer extends Movement {
        private final Long amount;
        Account destinationAccount;

        public Transfer(String id, Long amount, String description, Account destinationAccount) {
            super(id, description);
            this.amount = amount;
            this.destinationAccount = destinationAccount;
        }

        @Override
        public void executeMovement(Account originAccount) {
            originAccount.setBalance(originAccount.getBalance() - amount);
            destinationAccount.setBalance(destinationAccount.getBalance() + amount);
            log.info("Transfer " + amount + " to account " + destinationAccount.id + " from " + originAccount.id);
            log.info("Current balance account {}: {}", destinationAccount.getId(), destinationAccount.getBalance());
        }

        @Override
        public void undoMovement(Account originAccount) {
            destinationAccount.setBalance(destinationAccount.getBalance() - amount);
            originAccount.setBalance(originAccount.getBalance() + amount);
            log.info("Transfer cancelled " + amount + " to account " + destinationAccount.id + " from " + originAccount.id);
            log.info("Current balance account {}: {}", destinationAccount.getId(), destinationAccount.getBalance());
        }
    }

    public static class Annulment extends Movement {
        Movement originalMovement;

        public Annulment(String id, String description, Movement originalMovement) {
            super(id, description);
            this.originalMovement = originalMovement;
        }

        @Override
        public void executeMovement(Account account) {
            originalMovement.undoMovement(account);
        }

        @Override
        public void undoMovement(Account account) {
            originalMovement.executeMovement(account);
        }
    }

    @Data
    @RequiredArgsConstructor
    public static class Account {
        public static final ConcurrentHashMap<String, Movement> MOVEMENTS = new ConcurrentHashMap<>();
        final String id;
        Long balance = 0L;

        public void addMovement(Movement movement) {
            MOVEMENTS.put(movement.getId(), movement);
            movement.executeMovement(this);
            log.info("Current balance account {}: {}", id, balance);
        }
    }

    @Test
    public void test() {
        Account account = new Account(UUID.randomUUID().toString());
        Account account2 = new Account(UUID.randomUUID().toString());
        account.addMovement(MovementFactory.createDeposit("1", 1000L, "INGRESO"));
        account.addMovement(MovementFactory.createWithdrawal("2", 10L, "GASTOS VARIOS"));
        account.addMovement(MovementFactory.createTransfer("3", 100L, "TRANSFERENCIA", account2));
        account.addMovement(MovementFactory.createAnnulment("4", "ANULACIÓN GASTOS VARIOS", Account.MOVEMENTS.get("2")));
        account.addMovement(MovementFactory.createAnnulment("5", "ANULACIÓN TRANSFERENCIA", Account.MOVEMENTS.get("3")));
    }
    /* TODO
        Made the refactor to create new movement types, and avoid scalability issues applying the proper creational pattern.
        Remember, our code MUST follow SOLID Principles, so refactor the classes you need to accomplish it
     */


}
