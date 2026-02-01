package com.crud.tasks;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@EnableScheduling
@OpenAPIDefinition
@SpringBootApplication
public class TasksApplication {

    public static void main(String[] args) {
        SpringApplication.run(TasksApplication.class, args);
    }

    private static void weightAverage(List<Integer> grades, List<Integer> weights) {
        if (grades.isEmpty() || weights.isEmpty()) {
            throw new Error("List of grades and weights cannot be empty");
        }

        if (grades.size() != weights.size()) {
            throw new Error("Incorrect number of grades and weights");
        }

        for (int grade : grades) {
            if (grade < 1 || grade > 6) {
                throw new Error("Incorrect grade value");
            }
        }

        for (int weight : weights) {
            if (weight < 1 || weight > 10) {
                throw new Error("Incorrect weight value");
            }
        }

        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal weightSum = BigDecimal.ZERO;
        for (int i = 0; i < grades.size(); i++) {
            sum = sum.add(BigDecimal.valueOf(grades.get(i))
                    .multiply(BigDecimal.valueOf(weights.get(i))));
            weightSum = weightSum.add(BigDecimal.valueOf(weights.get(i)));
        }

        System.out.println("Average weight: " + sum.divide(weightSum, 2, RoundingMode.HALF_UP));
    }

    public static boolean isFirstNumber(int number) {
        if (number <= 1) {
            return false;
        }

        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static List<Integer> segregate(List<Integer> numbers) throws Exception {
        if (numbers.isEmpty()) {
            throw new Exception("Pusta lista");
        }
        return numbers.stream()
                .sorted(Comparator.comparingInt(a -> a))
                .toList();
    }

    public static List<Integer> segregateWithGas(List<Integer> numbers) throws Exception {
        if (numbers.isEmpty()) {
            throw new Exception("Pusta lista");
        }
        List<Integer> sorted = new ArrayList<>(numbers);

        for (int j = 0; j < sorted.size(); j++) {
            for (int i = 0; i < sorted.size() - 1 - j; i++) {
                int first = sorted.get(i);
                int second = sorted.get(i + 1);

                if (first > second) {
                    sorted.set(i + 1, first);
                    sorted.set(i, second);
                }

            }
        }
        return sorted;
    }

    public static int convertStringToInt(String input) {
        if (input == null || input.length() != 1) {
            throw new IllegalArgumentException("Podaj dokładnie jeden znak");
        }

        char c = input.charAt(0);

        if (!Character.isDigit(c)) {
            throw new IllegalArgumentException("Niedozwolony znak");
        }

        return Character.getNumericValue(c);
    }

    public static int convertStringToInteger(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Tekst jest pusty");
        }

        int result = 0;
        for(int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException("Niedozwolony znak");
            }

            int digit = Character.getNumericValue(c);

            result = result * 10 + digit;
        }

        return result;
    }

}
