package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ListMealStorage implements MealStorage {
    private final static AtomicInteger counter = new AtomicInteger();
    private List<Meal> meals = new ArrayList<>();

    @Override
    public void clear() {
        meals.clear();
    }

    @Override
    public void update(Meal meal) {
        for (int i = 0; i < meals.size(); i++) {
            if (meal.getId() == meals.get(i).getId()) {
                meals.set(i, meal);
            }
        }
    }

    @Override
    public void save(Meal meal) {
        int id = getId();
        meal.setId(id);
        meals.add(meal);
    }

    @Override
    public Meal get(int id) {
        return meals.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void delete(int id) {
        meals.removeIf(m -> m.getId() == id);
    }

    @Override
    public List<Meal> getAll() {
        return meals;
    }

    @Override
    public int size() {
        return counter.get();
    }

    private static int getId() {
        return counter.incrementAndGet();
    }
}
