package com.breadhardit.travelagencykata;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CreationalPatternExercicesSolution {
    // Interface Movement
    public interface Movement {
        String getId();
        void apply(Account account);
        void revert(Account account);
    }
    // Concrete Movement Implementations
    @Data
    @RequiredArgsConstructor
    public static class DepositMovement implements Movement {
        private final String id;
        private final Long amount;
        private final String description;
        @Override
        public void apply(Account account) {
            account.setBalance(account.getBalance() + amount);
            log.info("Deposit applied: {}. New balance: {}", amount, account.getBalance());
        }
        @Override
        public void revert(Account account) {
            account.setBalance(account.getBalance() - amount);
            log.info("Deposit annulled: {}. New balance: {}", amount, account.getBalance());
        }
    }
    @Data
    @RequiredArgsConstructor
    public static class WithdrawalMovement implements Movement {
        private final String id;
        private final Long amount;
        private final String description;
        @Override
        public void apply(Account account) {
            account.setBalance(account.getBalance() - amount);
            log.info("Withdrawal applied: {}. New balance: {}", amount, account.getBalance());
        }
        @Override
        public void revert(Account account) {
            account.setBalance(account.getBalance() + amount);
            log.info("Withdrawal annulled: {}. New balance: {}", amount, account.getBalance());
        }
    }
    @Data
    @RequiredArgsConstructor
    public static class TransferMovement implements Movement {
        private final String id;
        private final Long amount;
        private final String description;
        private final String destinationAccount;
        @Override
        public void apply(Account account) {
            account.setBalance(account.getBalance() - amount);
            log.info("Transfer of {} to {}. New balance: {}", amount, destinationAccount, account.getBalance());
        }
        @Override
        public void revert(Account account) {
            account.setBalance(account.getBalance() + amount);
            log.info("Transfer annulled. {} returned from {}. New balance: {}", amount, destinationAccount, account.getBalance());
        }
    }

    @Data
    @RequiredArgsConstructor
    public static class AnnulmentMovement implements Movement {
        private final String id;
        private final Movement originalMovement;

        @Override
        public void apply(Account account) {
            originalMovement.revert(account);
            log.info("Annulment applied for movement {}. New balance: {}", originalMovement.getId(), account.getBalance());
        }

        @Override
        public void revert(Account account) {
            originalMovement.apply(account);
            log.info("Annulment undone. Movement {} reapplied. New balance: {}", originalMovement.getId(), account.getBalance());
        }
    }

    // Factory Method
    public static class MovementFactory {
        public static Movement createMovement(String id, String type, Long amount, String description, String destinationAccount, Movement originalMovement) {
            return switch (type.toUpperCase()) {
                case "DEPOSIT" -> new DepositMovement(id, amount, description);
                case "WITHDRAWAL" -> new WithdrawalMovement(id, amount, description);
                case "TRANSFER" -> new TransferMovement(id, amount, description, destinationAccount);
                case "ANNULMENT" -> new AnnulmentMovement(id, originalMovement);
                default -> throw new IllegalArgumentException("Unknown movement type: " + type);
            };
        }
    }

    // Account class
    @Data
    @RequiredArgsConstructor
    public static class Account {
        public static final ConcurrentHashMap<String, Movement> MOVEMENTS = new ConcurrentHashMap<>();
        private final String id;
        private Long balance = 0L;

        public void addMovement(Movement movement) {
            MOVEMENTS.put(movement.getId(), movement);
            movement.apply(this);
        }
    }

    // Test case
    @Test
    public void test() {
        Account account = new Account(UUID.randomUUID().toString());

        Movement deposit = MovementFactory.createMovement("1", "DEPOSIT", 1000L, "Salary", null, null);
        Movement withdrawal = MovementFactory.createMovement("2", "WITHDRAWAL", 10L, "Coffee", null, null);
        Movement transfer = MovementFactory.createMovement("3", "TRANSFER", 100L, "Rent", "DEST-1234", null);

        account.addMovement(deposit);
        account.addMovement(withdrawal);
        account.addMovement(transfer);

        // Annulments
        Movement annulWithdrawal = MovementFactory.createMovement("4", "ANNULMENT", null, "Revert coffee purchase", null, withdrawal);
        account.addMovement(annulWithdrawal);

        Movement annulTransfer = MovementFactory.createMovement("5", "ANNULMENT", null, "Revert rent transfer", null, transfer);
        account.addMovement(annulTransfer);
    }
}
