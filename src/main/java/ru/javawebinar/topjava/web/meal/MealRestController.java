package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        LOG.info("getAll meal");
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getFiltered(String startDateStr, String endDateStr, String startTimeStr, String endTimeStr) {
        LOG.info("getFiltered meal");

        List<Meal> meals;

        if (startDateStr.equals("") && endDateStr.equals("")) {
            meals = service.getAll(AuthorizedUser.id());
        } else {
            LocalDate startDate = startDateStr.equals("") ? LocalDate.MIN : LocalDate.parse(startDateStr);
            LocalDate endDate = endDateStr.equals("") ? LocalDate.MAX : LocalDate.parse(endDateStr);
            meals = service.getFiltered(startDate, endDate, AuthorizedUser.id());
        }

        if (startTimeStr.equals("") && endTimeStr.equals("")) {
            return MealsUtil.getWithExceeded(meals, AuthorizedUser.getCaloriesPerDay());
        } else {
            LocalTime startTime = startTimeStr.equals("") ? LocalTime.MIN : LocalTime.parse(startTimeStr);
            LocalTime endTime = endTimeStr.equals("") ? LocalTime.MAX : LocalTime.parse(endTimeStr);
            return MealsUtil.getFilteredWithExceeded(meals, startTime, endTime, AuthorizedUser.getCaloriesPerDay());
        }
    }

    public Meal get(int id) {
        LOG.info("get meal " + id);
        return service.get(id, AuthorizedUser.id());
    }

    public Meal create(Meal meal) {
        meal.setId(null);
        meal.setUserId(AuthorizedUser.id());
        LOG.info("create meal " + meal);
        return service.save(meal);
    }

    public void delete(int id) {
        LOG.info("delete meal " + id);
        service.delete(id, AuthorizedUser.id());
    }

    public void update(Meal meal) {
        meal.setUserId(AuthorizedUser.id());
        LOG.info("update meal " + meal);
        service.update(meal);
    }
}
