package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> list = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExceed> list2 = getFilteredWithExceededOptional(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExceed> list3 = getFilteredWithExceededOptional2(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExceed> list4 = getFilteredWithExceededOptional2Loop(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println("Filtered by Loop:");
        list.forEach(System.out::println);
        System.out.println("Optional. Filtered by Stream:");
        list2.forEach(System.out::println);
        System.out.println("Optional 2. Filtered by Stream:");
        list3.forEach(System.out::println);
        System.out.println("Optional 2. Filtered by Loop:");
        list4.forEach(System.out::println);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            caloriesSumByDate.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), Integer::sum);
        }
        List<UserMealWithExceed> userMealsWithExceed = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                boolean exceed = caloriesSumByDate.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay;
                userMealsWithExceed.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceed));
            }
        }
        return userMealsWithExceed;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededOptional(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream()
                .collect(Collectors.groupingBy(m -> m.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));
        return mealList.stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(u -> new UserMealWithExceed(u.getDateTime(), u.getDescription(), u.getCalories(), caloriesSumByDate.get(u.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededOptional2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return mealList.stream()
                .collect(Collectors.toMap(
                        UserMeal::getDate,
                        um -> new AbstractMap.SimpleEntry<>(new ArrayList<>(Collections.singletonList(um)), um.getCalories()),
                        (entry, entry2) -> {
                            entry.getKey().addAll(entry2.getKey());
                            entry.setValue(entry.getValue() + entry2.getValue());
                            return entry;
                        }))
                .values().stream()
                .flatMap(entry -> entry.getKey()
                        .stream()
                        .filter(userMeal -> TimeUtil.isBetween(userMeal.getTime(), startTime, endTime))
                        .map(userMeal -> newUserMealWithExceed(userMeal, entry.getValue() > caloriesPerDay)))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededOptional2Loop(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> calByDate = new HashMap<>();
        List<UserMealWithExceed> userMealsWithExceed = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            if (calByDate.isEmpty()) {
                for (UserMeal um : mealList) {
                    calByDate.merge(um.getDate(), um.getCalories(), Integer::sum);
                }
            }
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                boolean exceed = calByDate.get(userMeal.getDate()) > caloriesPerDay;
                userMealsWithExceed.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceed));
            }
        }
        return userMealsWithExceed;
    }

    public static UserMealWithExceed newUserMealWithExceed(UserMeal userMeal, boolean exceed) {
        return new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceed);
    }
}
