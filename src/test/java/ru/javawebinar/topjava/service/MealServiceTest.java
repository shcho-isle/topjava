package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

@ContextConfiguration("classpath:spring/Spring-All-Module.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {
    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(USER_BREAKFAST.getId(), START_SEQ);
        MATCHER.assertEquals(USER_BREAKFAST, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        Meal meal = service.get(USER_BREAKFAST.getId(), START_SEQ + 1);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(USER_BREAKFAST.getId(), START_SEQ);

        Collection<Meal> meals = new ArrayList<>();
        meals.add(USER_DINNER);
        meals.add(USER_LUNCH);
        MATCHER.assertCollectionEquals(meals, service.getAll(START_SEQ));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(USER_BREAKFAST.getId(), START_SEQ + 1);
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        Collection<Meal> all = service.getBetweenDates(
                  LocalDate.of(2016, Month.DECEMBER, 26)
                , LocalDate.of(2016, Month.DECEMBER, 27)
                , START_SEQ);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_LUNCH, USER_BREAKFAST), all);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        Collection<Meal> all = service.getBetweenDateTimes(
                  LocalDateTime.of(2016, Month.DECEMBER, 27, 7, 0, 0)
                , LocalDateTime.of(2016, Month.DECEMBER, 27, 13, 30, 0)
                ,START_SEQ + 1);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_LUNCH, ADMIN_BREAKFAST), all);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Meal> all = service.getAll(START_SEQ);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_DINNER, USER_LUNCH, USER_BREAKFAST), all);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(USER_BREAKFAST);
        updated.setDateTime(LocalDateTime.of(2017, Month.JANUARY, 10, 0, 0));
        updated.setDescription("UpdatedName");
        updated.setCalories(666);
        service.update(updated, START_SEQ);
        MATCHER.assertEquals(updated, service.get(START_SEQ + 2, START_SEQ));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        Meal updated = new Meal(USER_BREAKFAST);
        updated.setDateTime(LocalDateTime.of(2017, Month.JANUARY, 10, 0, 0));
        updated.setDescription("UpdatedName");
        updated.setCalories(666);
        service.update(updated, START_SEQ + 1);
    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.of(2017, Month.JANUARY, 10, 0, 0), "newMeal", 2000);
        Meal created = service.save(newMeal, START_SEQ);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(newMeal, USER_DINNER, USER_LUNCH, USER_BREAKFAST), service.getAll(START_SEQ));
    }

}