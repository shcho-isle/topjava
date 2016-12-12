package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoMemory implements MealDao {
    private List<Meal> meals;
    private AtomicInteger count;

    public MealDaoMemory() {
        count = new AtomicInteger(0);
        meals = new CopyOnWriteArrayList<>();

        add(new Meal(LocalDateTime.of(2215, Month.MAY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public void add(Meal meal) {
        meal.setId(count.incrementAndGet());
        meals.add(meal);
    }

    @Override
    public void delete(int id) {
        for (Meal meal : meals)
            if (meal.getId() == id) {
                meals.remove(meal);
                break;
            }
    }

    @Override
    public void update(Meal meal) {
        for (int i = 0; i < meals.size(); i++)
            if (meals.get(i).getId() == meal.getId()) {
                meals.set(i, meal);
                return;
            }

        add(meal);
    }

    @Override
    public List<Meal> getAll() {
        return meals;
    }

    @Override
    public Meal getById(int id) {
        for (Meal meal : meals)
            if (meal.getId() == id)
                return meal;

        return null;
    }
}
