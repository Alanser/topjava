package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER, Role.ROLE_SUBSCRIBER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_USER, Role.ROLE_ADMIN);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", 1555, false, new Date(), EnumSet.of(Role.ROLE_USER, Role.ROLE_SUBSCRIBER));
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        return updated;
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "meals");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "meals").isEqualTo(expected);
    }
}
