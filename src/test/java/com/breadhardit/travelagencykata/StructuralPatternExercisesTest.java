package com.breadhardit.travelagencykata;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StructuralPatternExercisesTest {
    interface CalorieItem {
        double calories();
    }

    record Food(String name, long caloriesPer100g, long weightInGrams) implements CalorieItem {
        @Override
        public double calories() {
            return caloriesPer100g * (weightInGrams / 100.0);
        }
    }

    record Dish(String name, List<Food> ingredients) implements CalorieItem {
        Dish {
            ingredients = List.copyOf(ingredients);
        }

        @Override
        public double calories() {
            return totalCalories(ingredients);
        }
    }

    private static double totalCalories(List<? extends CalorieItem> items) {
        return items.stream().mapToDouble(CalorieItem::calories).sum();
    }

    @Test
    void addsCaloriesFromDishesAndIndividualFoods() {
        Food potato = new Food("Potato", 80, 300);
        Food bread = new Food("Bread", 320, 100);
        Food tomato = new Food("Tomato", 85, 50);
        Food burger = new Food("Burger", 340, 120);
        Food lettuce = new Food("Lettuce", 16, 120);
        Food apple = new Food("Apple", 34, 180);
        Food ketchup = new Food("Ketchup", 180, 15);
        Dish completeBurger = new Dish("Complete burger", List.of(potato, bread, burger));
        Dish greenSalad = new Dish("Green salad", List.of(lettuce, tomato));
        List<CalorieItem> menu = List.of(greenSalad, completeBurger, ketchup, apple);

        assertEquals(1117.9, totalCalories(menu), 0.001);
    }
}
