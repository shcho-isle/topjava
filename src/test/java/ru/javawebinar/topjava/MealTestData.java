package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {
    public static final Meal USER_BREAKFAST = new Meal(START_SEQ + 2, LocalDateTime.of(2016, Month.DECEMBER, 26, 8, 0), "Юзер завтрак", 900);
    public static final Meal USER_LUNCH = new Meal(START_SEQ + 3, LocalDateTime.of(2016, Month.DECEMBER, 27, 14, 0), "Юзер обед", 1500);
    public static final Meal USER_DINNER = new Meal(START_SEQ + 4, LocalDateTime.of(2016, Month.DECEMBER, 28, 19, 0), "Юзер ужин", 600);
    public static final Meal ADMIN_BREAKFAST = new Meal(START_SEQ + 5, LocalDateTime.of(2016, Month.DECEMBER, 27, 7, 30), "Админ завтрак", 600);
    public static final Meal ADMIN_LUNCH = new Meal(START_SEQ + 6, LocalDateTime.of(2016, Month.DECEMBER, 27, 13, 30), "Админ обед", 1000);
    public static final Meal ADMIN_DINNER = new Meal(START_SEQ + 7, LocalDateTime.of(2016, Month.DECEMBER, 27, 18, 30), "Админ ужин", 400);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual || (
                               Objects.equals(expected.getDescription(), actual.getDescription())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getCalories(), actual.getCalories())
                            && Objects.equals(expected.getDateTime(), actual.getDateTime())
            )
    );
}
