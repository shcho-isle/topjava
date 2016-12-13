package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoMemory implements MealDao {
    private Map<Integer, Meal> meals;
    private AtomicInteger count;

    public MealDaoMemory() {
        count = new AtomicInteger(0);
        meals = new ConcurrentHashMap<>();

        add(new Meal(LocalDateTime.of(2215, Month.MAY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public void add(Meal meal) {
        int id = count.incrementAndGet();
        meal.setId(id);
        meals.put(id, meal);
    }

    @Override
    public void delete(int id) {
        if (meals.containsKey(id)) {
            meals.remove(id);
        }
    }

    @Override
    public void update(Meal meal) {
        int id = meal.getId();

        if (meals.containsKey(id))
            meals.put(id, meal);
        else
            add(meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal getById(int id) {
            return meals.getOrDefault(id, null);
    }
}
