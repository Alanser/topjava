package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> {
            this.save(1, new Meal(meal.getDateTime(), meal.getDescription() + " (User 1)", meal.getCalories()));
            this.save(2, new Meal(meal.getDateTime(), meal.getDescription() + " (User 2)", meal.getCalories()));
        });
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save {} by userID{}", meal, userId);
        Map<Integer, Meal> userMeals = getUserMeals(userId);
        repository.putIfAbsent(userId, userMeals);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMeals.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        return userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int mealId) {
        log.info("delete {} by userID {}", mealId, userId);
        return getUserMeals(userId).remove(mealId) != null;
    }

    @Override
    public Meal get(int userId, int mealId) {
        log.info("get {} by userID {}", mealId, userId);
        return getUserMeals(userId).get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return getFiltered(userId, m -> true);
    }

    @Override
    public List<Meal> getFilteredByDate(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getFilteredByDate");
        return getFiltered(userId, m -> DateTimeUtil.isBetween(m.getDate(), startDate, endDate));
    }

    private Map<Integer, Meal> getUserMeals(int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals == null ? new HashMap<>() : userMeals;
    }

    private List<Meal> getFiltered(int userId, Predicate<Meal> filter) {
        return getUserMeals(userId).values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

