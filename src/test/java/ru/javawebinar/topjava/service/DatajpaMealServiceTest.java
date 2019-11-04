package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles("datajpa")
public class DatajpaMealServiceTest extends AbstractMealServiceTest {
    @Test
    public void getWithUser() throws Exception {
        Meal meal = service.getWithUser(MEAL1_ID, USER_ID);
        UserTestData.assertMatch(meal.getUser(), USER);
    }
}
