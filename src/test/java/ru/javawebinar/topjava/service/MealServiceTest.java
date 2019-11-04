package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.REPOSITORY_IMPLEMENTATION;

@ActiveProfiles(REPOSITORY_IMPLEMENTATION)
public class MealServiceTest extends AbstractMealServiceTest {
}
