package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal get(int mealId) {
        log.info("get {}", mealId);
        return service.get(authUserId(), mealId);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        return service.create(authUserId(), meal);
    }

    public void delete(int mealId) {
        log.info("delete {}", mealId);
        service.delete(authUserId(), mealId);
    }

    public void update(Meal meal, int mealId) {
        log.info("update {} with id={}", meal, mealId);
        service.update(authUserId(), meal);
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public List<MealTo> getFiltered(String startDate, String endDate, String startTime, String endTime) {
        log.info("getFiltered");
        return MealsUtil.getFilteredTos(
                service.getFilteredByDate(authUserId(),
                        startDate == null || startDate.isEmpty() ? LocalDate.MIN : LocalDate.parse(startDate),
                        endDate == null || endDate.isEmpty() ? LocalDate.MAX : LocalDate.parse(endDate)),
                authUserCaloriesPerDay(),
                startTime == null || startTime.isEmpty() ? LocalTime.MIN : LocalTime.parse(startTime),
                endTime == null || endTime.isEmpty() ? LocalTime.MAX : LocalTime.parse(endTime));
    }
}