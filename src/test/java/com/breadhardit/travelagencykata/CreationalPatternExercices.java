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
    public static class Movement {
        public enum MovementType {DEPOSIT,WITHDRAWAL}
        String id;
        MovementType type;
        Long amount;
        String description;
    }
    @Data
    @RequiredArgsConstructor
    public static class Account {
        public static final ConcurrentHashMap<String,Movement> MOVEMENTS = new ConcurrentHashMap<>();
        final String id;
        Long balance = 0L;
        public void addMovement(Movement movement) {
            MOVEMENTS.put(movement.getId(),movement);
            if (Movement.MovementType.DEPOSIT.equals(movement.getType())) {
                balance += movement.getAmount();
            } else {
                balance -= movement.getAmount();
            }
            log.info("Current balance: {}",balance);
        }
    }
    @Test
    public void test() {
        Account account = new Account(UUID.randomUUID().toString());
        account.addMovement(Movement.builder().id("1").type(Movement.MovementType.DEPOSIT).amount(1000L).description("INGRESO").build());
        account.addMovement(Movement.builder().id("1").type(Movement.MovementType.WITHDRAWAL).amount(10L).description("GASTOS VARIOS").build());
    }
    /* TODO
        Made the refactor to create new movement types, and avoid scalability issues applying the proper creational pattern.
        Remember, our code MUST follow SOLID Principles, so refactor the classes you need to accomplish it
     */


}
