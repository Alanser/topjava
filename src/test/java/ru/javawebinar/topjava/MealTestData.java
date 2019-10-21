package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_MEAL_ID_1 = START_SEQ + 2;
    public static final int USER_MEAL_ID_2 = START_SEQ + 3;
    public static final int USER_MEAL_ID_3 = START_SEQ + 4;
    public static final int USER_MEAL_ID_4 = START_SEQ + 5;
    public static final int USER_MEAL_ID_5 = START_SEQ + 6;
    public static final int USER_MEAL_ID_6 = START_SEQ + 7;

    public static final int ADMIN_MEAL_ID_1 = START_SEQ + 8;
    public static final int ADMIN_MEAL_ID_2 = START_SEQ + 9;
    public static final int ADMIN_MEAL_ID_3 = START_SEQ + 10;
    public static final int ADMIN_MEAL_ID_4 = START_SEQ + 11;
    public static final int ADMIN_MEAL_ID_5 = START_SEQ + 12;
    public static final int ADMIN_MEAL_ID_6 = START_SEQ + 13;

    public static final Meal USER_MEAL_1 = new Meal(USER_MEAL_ID_1, LocalDateTime.parse("2019-10-19T08:00:00"), "Завтрак (USER)", 700);
    public static final Meal USER_MEAL_2 = new Meal(USER_MEAL_ID_2, LocalDateTime.parse("2019-10-19T13:00:00"), "Обед (USER)", 1000);
    public static final Meal USER_MEAL_3 = new Meal(USER_MEAL_ID_3, LocalDateTime.parse("2019-10-19T20:00:00"), "Ужин (USER)", 200);
    public static final Meal USER_MEAL_4 = new Meal(USER_MEAL_ID_4, LocalDateTime.parse("2019-10-20T08:00:00"), "Завтрак (USER)", 700);
    public static final Meal USER_MEAL_5 = new Meal(USER_MEAL_ID_5, LocalDateTime.parse("2019-10-20T13:00:00"), "Обед (USER)", 900);
    public static final Meal USER_MEAL_6 = new Meal(USER_MEAL_ID_6, LocalDateTime.parse("2019-10-20T20:00:00"), "Ужин (USER)", 200);

    public static final Meal ADMIN_MEAL_1 = new Meal(ADMIN_MEAL_ID_1, LocalDateTime.parse("2019-10-19T08:00:00"), "Завтрак (ADMIN)", 700);
    public static final Meal ADMIN_MEAL_2 = new Meal(ADMIN_MEAL_ID_2, LocalDateTime.parse("2019-10-19T13:00:00"), "Обед (ADMIN)", 1000);
    public static final Meal ADMIN_MEAL_3 = new Meal(ADMIN_MEAL_ID_3, LocalDateTime.parse("2019-10-19T20:00:00"), "Ужин (ADMIN)", 200);
    public static final Meal ADMIN_MEAL_4 = new Meal(ADMIN_MEAL_ID_4, LocalDateTime.parse("2019-10-20T08:00:00"), "Завтрак (ADMIN)", 700);
    public static final Meal ADMIN_MEAL_5 = new Meal(ADMIN_MEAL_ID_5, LocalDateTime.parse("2019-10-20T13:00:00"), "Обед (ADMIN)", 900);
    public static final Meal ADMIN_MEAL_6 = new Meal(ADMIN_MEAL_ID_6, LocalDateTime.parse("2019-10-20T20:00:00"), "Ужин (ADMIN)", 200);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
