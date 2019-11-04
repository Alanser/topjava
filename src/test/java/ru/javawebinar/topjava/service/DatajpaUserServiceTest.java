package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;

@ActiveProfiles("datajpa")
public class DatajpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void getWithMeals() throws Exception {
        MealTestData.assertMatch(service.getWithMeals(UserTestData.USER_ID).getMeals(), MealTestData.MEALS);
    }
}
