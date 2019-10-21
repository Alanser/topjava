package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL_ID_1, USER_ID);
        assertMatch(meal, USER_MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getSomeoneElses() {
        service.get(USER_MEAL_ID_1, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_ID_1, USER_ID);
        assertMatch(service.getAll(USER_ID), USER_MEAL_2, USER_MEAL_3, USER_MEAL_4, USER_MEAL_5, USER_MEAL_6);
    }

    @Test(expected = NotFoundException.class)
    public void deleteSomeoneElses() {
        service.delete(USER_MEAL_ID_1, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> meals = service.getBetweenDates(LocalDate.parse("2019-10-18"), LocalDate.parse("2019-10-19"), USER_ID);
        assertMatch(meals, USER_MEAL_1, USER_MEAL_2, USER_MEAL_3);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, USER_MEAL_1, USER_MEAL_2, USER_MEAL_3, USER_MEAL_4, USER_MEAL_5, USER_MEAL_6);
    }

    @Test
    public void update() {
        Meal updated = new Meal(USER_MEAL_1);
        updated.setDateTime(LocalDateTime.parse("2019-10-19T08:30:00"));
        updated.setDescription("Updated Description");
        updated.setCalories(2000);
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_MEAL_ID_1, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateSomeoneElses() {
        Meal updated = new Meal(USER_MEAL_1);
        updated.setDateTime(LocalDateTime.parse("2019-10-19T08:30:00"));
        updated.setDescription("Updated Description");
        updated.setCalories(2000);
        service.update(updated, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "Новая еда", 1500);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), USER_MEAL_1, USER_MEAL_2, USER_MEAL_3, USER_MEAL_4, USER_MEAL_5, USER_MEAL_6, newMeal);
    }
}
