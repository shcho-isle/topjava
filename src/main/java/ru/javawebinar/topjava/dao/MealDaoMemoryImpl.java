package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;

public class MealDaoMemoryImpl implements MealDao {
    private List<Meal> meals;
    private int count;

    public MealDaoMemoryImpl() {
        count = 0;
        meals = new ArrayList<>();


    }

    @Override
    public void add(Meal meal) {
        meals.add(meal);
    }

    @Override
    public void delete(int id) {
        for (Meal meal: meals) {
            if (meal.getId() == id) {
                meals.remove(meal);
                break;
            }
        }
    }

    @Override
    public void update(Meal meal) {
        int index = meals.indexOf(meal);
        meals.set(index, meal);
    }

    @Override
    public List<Meal> getAll() {
        return meals;
    }

    @Override
    public Meal getById(int id) {
        for (Meal meal: meals) {
            if (meal.getId() == id) {
                return meal;
            }
        }

        return null;
    }
}
