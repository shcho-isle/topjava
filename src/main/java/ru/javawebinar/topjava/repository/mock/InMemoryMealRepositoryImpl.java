package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
        } else {
            Meal oldMeal = repository.get(meal.getId());
            if (oldMeal.getUserId().equals(meal.getUserId())) {
                repository.put(meal.getId(), meal);
            } else {
                meal = null;
            }
        }

        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal != null && meal.getUserId().equals(userId)) {
            repository.remove(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal.getUserId().equals(userId)) {
            return meal;
        } else {
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFiltered(LocalDate.MIN, LocalDate.MAX, userId);
    }

    public List<Meal> getFiltered(LocalDate startDate, LocalDate endDate, int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId) && DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());
    }
}

