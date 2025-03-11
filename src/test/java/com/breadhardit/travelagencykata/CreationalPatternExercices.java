package com.breadhardit.travelagencykata;

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
    public interface Movement {
        String getId();
        public void makeMovement(Account account);
    }
    @Data
    @RequiredArgsConstructor
    public static class DepositMovement implements Movement{
        private final String id;
        private final Long amount;
        private final String description;
        public void makeMovement(Account account){
            account.setBalance(account.getBalance() + amount);
        }
    }
    @Data
    @RequiredArgsConstructor
    public static class WithdrawalMovement implements Movement{
        private final String id;
        private final Long amount;
        private final String description;
        public void makeMovement(Account account){
            account.setBalance(account.getBalance() - amount);
        }
    }
    @Data
    @RequiredArgsConstructor
    public static class TransferMovement implements Movement{
        private final String id;
        private final Long amount;
        private final String description;
        private final String destinationAccount;
        public void makeMovement(Account account){
            account.setBalance(account.getBalance() - amount);
            log.info(destinationAccount);
        }
    }
    @Data
    @RequiredArgsConstructor
    public static class AnnulmentMovement implements Movement{
        private final String id;
        private final Movement originalMovement;
        public void makeMovement(Account account){
            log.info(originalMovement.getId());
        }
    }

    @Data
    @RequiredArgsConstructor
    public static class Account {
        public static final ConcurrentHashMap<String,Movement> MOVEMENTS = new ConcurrentHashMap<>();
        private final String id;
        Long balance = 0L;
        
        public void addMovement(Movement movement) {
            movement.makeMovement(this);
            log.info("Current balance: {}",balance);
        }
    }

    //Factory
    public static class MovementFactory{
        public static Movement createMovement(String id, Long amount, String type, String description, String destinationAccount, Movement originalMovement){
            return switch (type.toUpperCase()){
                case "DEPOSIT" -> new DepositMovement(id, amount, description);
                case "WITHDRAWAL" -> new WithdrawalMovement(id, amount, description);
                case "TRANSFER" -> new TransferMovement(id, amount, description, destinationAccount);
                case "ANNULMENT" -> new AnnulmentMovement(id, originalMovement);
                default -> throw new IllegalArgumentException();
            };
        }
    }

    @Test
    public void test() {
        Account account = new Account(UUID.randomUUID().toString());

        Movement deposit = MovementFactory.createMovement("1",1000L, "DEPOSIT", "INGRESO", null, null);
        Movement transfer = MovementFactory.createMovement("1",10L,"TRANSFER","GASTOS VARIOS", null, null);

        account.addMovement(deposit);
        account.addMovement(transfer);
    }
    /* TODO
        Made the refactor to create new movement types, and avoid scalability issues applying the proper creational pattern.
        Remember, our code MUST follow SOLID Principles, so refactor the classes you need to accomplish it
     */


}
