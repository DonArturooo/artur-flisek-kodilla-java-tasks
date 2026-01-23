package com.crud.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
}
