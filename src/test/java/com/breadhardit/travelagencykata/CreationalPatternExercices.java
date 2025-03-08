package com.breadhardit.travelagencykata;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CreationalPatternExercices {
    /*
     * Banking accounts has movements. And each movement can be a deposit or withdrawal
     * After a few months operating, we need to create new types of movements:
     *   - TRANSFER: It's a withdrawal, but we need the destination account number
     *   - ANNULMENT: It cancels a movement, so, we need the original movement
     */
    @Data
    public static abstract class Movement {
        public enum MovementType {DEPOSIT,WITHDRAWAL,TRANSFER, ANNULMENT}
        String id;
        MovementType type;
        Long amount;
        String description;

        public Movement(String id, MovementType type, Long amount, String description) {
            this.id = id;
            this.type = type;
            this.amount = amount;
            this.description = description;
        }

        // Se podría implementar con una interfaz yo creo.
        protected abstract void executeMovement(Account account);
        protected abstract void undoMovement(Account account);
    }


    public static class Deposit extends Movement{

        public Deposit(String id, Long amount, String description) {
            super(id, MovementType.DEPOSIT, amount, description);
        }

        @Override
        protected void executeMovement(Account account) {
            account.setBalance(account.getBalance() + this.amount);
        }

        @Override
        protected void undoMovement(Account account) {
            account.setBalance(account.getBalance() - this.amount);
        }
    }

    public static class Withdrawal extends Movement{

        public Withdrawal(String id, Long amount, String description) {
            super(id, MovementType.WITHDRAWAL, amount, description);

        }

        @Override
        protected void executeMovement(Account account) {
            account.setBalance(account.getBalance() - this.amount);
        }

        @Override
        protected void undoMovement(Account account) {
            account.setBalance(account.getBalance() + this.amount);
        }
    }

    public static class Transfer extends Movement{

        Account destinationAccount;
        public Transfer(String id, Long amount, String description, Account destinationAccount) {
            super(id, MovementType.TRANSFER, amount, description);
            this.destinationAccount = destinationAccount;
        }

        @Override
        protected void executeMovement(Account account) {
            account.setBalance(account.getBalance() - this.amount);
            destinationAccount.setBalance(destinationAccount.getBalance() + this.amount);
            log.info("Destination account balance: {}", destinationAccount.getBalance());
        }

        @Override
        protected void undoMovement(Account account) {
            account.setBalance(account.getBalance() + this.amount);
            destinationAccount.setBalance(destinationAccount.getBalance() - this.amount);
            log.info("Destination account balance: {}", destinationAccount.getBalance());
        }

    }

    public static class Annulment extends Movement{
        Movement movement;
        public Annulment(String id, Movement movement) {
            super(id, MovementType.ANNULMENT, movement.getAmount(), movement.getDescription());
            this.movement = movement;
        }

        @Override
        protected void executeMovement(Account account) {
            movement.undoMovement(account);
        }

        @Override
        protected void undoMovement(Account account) {
            movement.executeMovement(account);
        }
    }


    @Data
    @RequiredArgsConstructor
    public static class Account {
        public static final ConcurrentHashMap<String,Movement> MOVEMENTS = new ConcurrentHashMap<>();
        final String id;
        Long balance = 0L;
        public void addMovement(Movement movement) {
            MOVEMENTS.put(movement.getId(), movement);
            movement.executeMovement(this);
            log.info("[{}] Current balance: {}",movement.getType().toString(),this.getBalance());
        }
    }

    public static class MovementFactory {
        public static Movement createMovement(Movement.MovementType type, String id, Long amount, String description, Account destinationAccount, Movement originalMovement) {
            return switch (type) {
                case DEPOSIT -> new Deposit(id, amount, description);
                case WITHDRAWAL -> new Withdrawal(id, amount, description);
                case TRANSFER -> new Transfer(id, amount, description, destinationAccount);
                case ANNULMENT -> new Annulment(id, originalMovement);
            };
        }
    }

    @Test
    public void test() {
        Account account = new Account(UUID.randomUUID().toString());
        Account destinationAccount = new Account(UUID.randomUUID().toString());

        Movement deposit = MovementFactory.createMovement(Movement.MovementType.DEPOSIT, UUID.randomUUID().toString(), 100L, "Deposit", null, null);
        account.addMovement(deposit);
        Movement withdrawal = MovementFactory.createMovement(Movement.MovementType.WITHDRAWAL, UUID.randomUUID().toString(), 50L, "Withdrawal", null, null);
        account.addMovement(withdrawal);
        Movement transfer = MovementFactory.createMovement(Movement.MovementType.TRANSFER, UUID.randomUUID().toString(), 50L, "Transfer", destinationAccount, null);
        account.addMovement(transfer);


        Movement annulmentWithdrawal = MovementFactory.createMovement(Movement.MovementType.ANNULMENT, UUID.randomUUID().toString(), 50L, "Annulment", null, withdrawal);
        account.addMovement(annulmentWithdrawal);
        Movement annulmentTransfer = MovementFactory.createMovement(Movement.MovementType.ANNULMENT, UUID.randomUUID().toString(), 50L, "Annulment", destinationAccount, transfer);
        account.addMovement(annulmentTransfer);
    }

    /* TODO
        Made the refactor to create new movement types, and avoid scalability issues applying the proper creational pattern.
        Remember, our code MUST follow SOLID Principles, so refactor the classes you need to accomplish it
     */


}
