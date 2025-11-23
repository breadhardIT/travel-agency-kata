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
     *   - TRANSFER: It's a withdrawal, bet we need the destination account number
     *   - ANNULMENT: It cancels a movement, so, we need the original movement
     */
    @Data
    @Builder
    public static class DepositMovement implements Movement {
        private String id;
        private Long amount;
        private String description;

        public DepositMovement(String id, Long amount, String description){
            this.id = id;
            this.amount = amount;
            this.description = description;
        }

        public void apply(Account account){
            account.setBalance(account.getBalance() + amount);
        }

        public void revert(Account account){
            account.setBalance(account.getBalance() - amount);
        }
    }

    @Data
    @Builder
    public static class WithdrawalMovement implements Movement {
        private String id;
        private Long amount;
        private String description;

        public WithdrawalMovement(String id, Long amount, String description){
            this.id = id;
            this.amount = amount;
            this.description = description;
        }

        public void apply(Account account){
            account.setBalance(account.getBalance() - amount);
        }

        public void revert(Account account){
            account.setBalance(account.getBalance() + amount);
        }
    }

    @Data
    @Builder
    public static class TransferMovement implements Movement {
        private String id;
        private Long amount;
        private String description;
        private Account destinationAccount;

        public TransferMovement(String id, Long amount, String description, Account destinationAccount){
            this.id = id;
            this.amount = amount;
            this.destinationAccount = destinationAccount;
            this.description = description;
        }

        public void apply(Account account){
            account.setBalance(account.getBalance() - amount);
            destinationAccount.setBalance(destinationAccount.getBalance() + amount);
        }

        public void revert(Account account){
            account.setBalance(account.getBalance() + amount);
            destinationAccount.setBalance(destinationAccount.getBalance() - amount);
        }
    }

    @Data
    @Builder
    public static class AnullmentMovement implements Movement {
        private String id;
        private Long amount;
        private String description;
        private Movement movementToRevert;

        public AnullmentMovement(String id, Long amount, String description, Movement movementToRevert){
            this.id = id;
            this.amount = amount;
            this.description = description;
            this.movementToRevert = movementToRevert;
        }

        public void apply(Account account){
            movementToRevert.revert(account);
        }

        public void revert(Account account){
            movementToRevert.apply(account);
        }
    }

    public enum MovementType{DEPOSIT, WITHDRAWAL, TRANSFER, ANULLMENT}

    public interface Movement{
        void apply(Account account);
        void revert(Account account);

        String getId();
        static MovementBuilder builder(){
            return new MovementBuilder();
        }
    }

    public static class MovementBuilder{
        private String id;
        private MovementType type;
        private Long amount;
        private String description;
        private Account destinationAccount;
        private Movement movementToRevert;

        public Movement build(){
            return switch (type){
                case DEPOSIT -> new DepositMovement(id, amount, description);
                case WITHDRAWAL -> new WithdrawalMovement(id, amount, description);
                case TRANSFER -> new TransferMovement(id, amount, description, destinationAccount);
                case ANULLMENT -> new AnullmentMovement(id, amount, description, movementToRevert);
                default -> null;
            };
        }

        public MovementBuilder id(String id){
            this.id = id;
            return this;
        }

        public MovementBuilder type(MovementType type){
            this.type = type;
            return this;
        }

        public MovementBuilder amount(Long amount){
            this.amount = amount;
            return this;
        }

        public MovementBuilder description(String description){
            this.description = description;
            return this;
        }

        public MovementBuilder destinationAccount(Account destinationAccount){
            this.destinationAccount = destinationAccount;
            return this;
        }

        public MovementBuilder movementToRevert(Movement movementToRevert){
            this.movementToRevert = movementToRevert;
            return this;
        }
    }


    @Data
    @RequiredArgsConstructor
    public static class Account {
        public static final ConcurrentHashMap<String,Movement> MOVEMENTS = new ConcurrentHashMap<>();
        private final String id;
        private Long balance = 0L;

        public void addMovement(Movement movement) {
            MOVEMENTS.put(movement.getId(),movement);
            movement.apply(this);
            log.info("Current balance: {}",balance);
        }

        public void setBalance(Long balance){
            this.balance = balance;
        }

        public Long getBalance(){
            return this.balance;
        }
    }
    @Test
    public void test() {
        Account account = new Account(UUID.randomUUID().toString());
        account.addMovement(Movement.builder().id("1").type(MovementType.DEPOSIT).amount(1000L).description("INGRESO").build());
        account.addMovement(Movement.builder().id("1").type(MovementType.WITHDRAWAL).amount(10L).description("GASTOS VARIOS").build());
        account.addMovement(Movement.builder().id("1").type(MovementType.TRANSFER).
                amount(20L).description("RENT").destinationAccount(new Account(UUID.randomUUID().toString())).build());
    }
    /* TODO
        Made the refactor to create new movement types, and avoid scalability issues applying the proper creational pattern.
        Remember, our code MUST follow SOLID Principles, so refactor the classes you need to accomplish it
     */


}
