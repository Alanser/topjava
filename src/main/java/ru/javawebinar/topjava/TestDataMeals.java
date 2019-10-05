package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class TestDataMeals {
    public static List<Meal> getMeals() {
        return Arrays.asList(
                new Meal(LocalDateTime.of(2019, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2019, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2019, Month.MAY, 30, 16, 0), "Полдник", 300),
                new Meal(LocalDateTime.of(2019, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2019, Month.MAY, 31, 10, 0), "Завтрак", 600),
                new Meal(LocalDateTime.of(2019, Month.MAY, 31, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2019, Month.MAY, 31, 20, 0), "Ужин", 300),
                new Meal(LocalDateTime.of(2019, Month.APRIL, 1, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2019, Month.APRIL, 1, 13, 0), "Обед", 1200),
                new Meal(LocalDateTime.of(2019, Month.APRIL, 1, 20, 0), "Ужин", 520),
                new Meal(LocalDateTime.of(2019, Month.APRIL, 2, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2019, Month.APRIL, 2, 13, 0), "Обед", 800),
                new Meal(LocalDateTime.of(2019, Month.APRIL, 2, 20, 0), "Ужин", 600)
        );
    }
}
