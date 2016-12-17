package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;

/**
 * User: gkislin
 * Date: 05.08.2015
 *
 * @link http://caloriesmng.herokuapp.com/
 * @link https://github.com/JavaOPs/topjava
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Topjava Enterprise!");

//        UserRepository repository = new InMemoryUserRepositoryImpl();
//        for (User user : repository.getAll()) {
//            System.out.println(user);
//        }

        MealRepository mealRepository = new InMemoryMealRepositoryImpl();
        for (Meal meal: mealRepository.getAll(1)) {
            System.out.println(meal);
        }
    }
}
