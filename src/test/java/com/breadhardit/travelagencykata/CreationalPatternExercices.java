package com.breadhardit.travelagencykata;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

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
    @SuperBuilder
    public static abstract class Movement {
        private final String id;

        public Movement(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        abstract void applyMovement(Account account);

        abstract void revertMovement(Account account);

    }

    @Data
    @SuperBuilder
    public static abstract class AmountMovement extends Movement {
        private final Long amount;
        private final String description;

        public Long getAmount() {
            return this.amount;
        }
    }


    @Data
    @SuperBuilder
    public static class DepositMovement extends AmountMovement {
        @Override
        public void applyMovement(Account account) {
            account.setBalance(account.getBalance() + getAmount());
        }

        @Override
        public void revertMovement(Account account) {
            account.setBalance(account.getBalance() - getAmount());
        }
    }

    @Data
    @SuperBuilder
    public static class WithdrawalMovement extends AmountMovement {
        @Override
        public void applyMovement(Account account) {
            account.setBalance(account.getBalance() - getAmount());
        }

        @Override
        public void revertMovement(Account account) {
            account.setBalance(account.getBalance() + getAmount());
        }
    }

    @Data
    @SuperBuilder
    public static class TransferMovement extends WithdrawalMovement {
        private final String destinationAccountNumber;

        @Override
        public void applyMovement(Account account) {
            super.applyMovement(account);
            log.info("Transeferencia hecha a {}", destinationAccountNumber);
        }

        @Override
        public void revertMovement(Account account) {
            super.revertMovement(account);
            log.info("Transferencia hecha a {} cancelada", destinationAccountNumber);
        }

    }

    @Data
    @SuperBuilder
    public static class AnnulmentMovement extends Movement {
        private final Movement originalMovement;
        @Override
        public void applyMovement(Account account) {
            this.originalMovement.revertMovement(account);
        }

        @Override
        public void revertMovement(Account account) {
            this.originalMovement.applyMovement(account);
        }
    }

    public static class MovementFactory {
        public static Movement buildMovement(String id, Long amount, String description, String type,
                                             String destinationAccountNumber, Movement originalMovement) {
            return switch (type.toUpperCase()) {
                case "DEPOSIT" -> DepositMovement.builder()
                        .id(id)
                        .amount(amount)
                        .description(description)
                        .build();
                case "WITHDRAWAL" -> WithdrawalMovement.builder()
                        .id(id)
                        .amount(amount)
                        .description(description)
                        .build();
                case "TRANSFER" -> TransferMovement.builder()
                        .id(id)
                        .amount(amount)
                        .description(description)
                        .destinationAccountNumber(destinationAccountNumber)
                        .build();
                case "ANNULMENT" -> AnnulmentMovement.builder()
                        .id(id)
                        .originalMovement(originalMovement)
                        .build();
                default -> null;
            };
        }
    }

    @Data
    @RequiredArgsConstructor
    public static class Account {
        public static final ConcurrentHashMap<String,Movement> MOVEMENTS = new ConcurrentHashMap<>();
        final String id;
        Long balance = 0L;
        public void addMovement(Movement movement) {
            MOVEMENTS.put(movement.getId(),movement);
            movement.applyMovement(this);
            log.info("Current balance: {}",balance);
        }
    }
    @Test
    public void test() {
        Account account = new Account(UUID.randomUUID().toString());


        Movement deposit = MovementFactory.buildMovement("1", 1000L, "Salary", "DEPOSIT",
                null, null);
        Movement withdrawal = MovementFactory.buildMovement("2", 10L, "Coffee", "WITHDRAWAL",
                null, null);
        Movement transfer = MovementFactory.buildMovement("3", 100L, "Rent", "TRANSFER",
                "DEST-1234", null);

        account.addMovement(deposit);
        account.addMovement(withdrawal);
        account.addMovement(transfer);

        Movement annulWithdrawal = MovementFactory.buildMovement("4", null,
                "Revert coffee purchase", "ANNULMENT", null, withdrawal);

        account.addMovement(annulWithdrawal);

        Movement annulTransfer = MovementFactory.buildMovement("5", null, "Revert rent transfer",
                "ANNULMENT", null, transfer);
        account.addMovement(annulTransfer);
    }
    /* TODO
        Made the refactor to create new movement types, and avoid scalability issues applying the proper creational pattern.
        Remember, our code MUST follow SOLID Principles, so refactor the classes you need to accomplish it
     */
}
