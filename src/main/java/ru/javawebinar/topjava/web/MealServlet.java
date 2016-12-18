package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx;
    private MealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(
                null
                , LocalDateTime.parse(request.getParameter("dateTime"))
                , request.getParameter("description")
                , Integer.valueOf(request.getParameter("calories")));

        if (id.isEmpty()) {
            LOG.info("Create {}", meal);
            controller.create(meal);
        } else {
            LOG.info("Update {}", meal);
            meal.setId(Integer.valueOf(id));
            controller.update(meal);
        }

        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("meals", controller.getAll());
            request.getRequestDispatcher("meals.jsp").forward(request, response);

        } else if ("login".equals(action)) {
            AuthorizedUser.setId(Integer.parseInt(request.getParameter("authUser")));
            response.sendRedirect("meals");

        } else if ("filter".equals(action)) {
            LOG.info("filter");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");

            request.setAttribute("meals", controller.getFiltered(startDate, endDate, startTime, endTime));
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("startTime", startTime);
            request.setAttribute("endTime", endTime);
            request.getRequestDispatcher("meals.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            controller.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create")
                    ? new Meal(AuthorizedUser.id(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000)
                    : controller.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("meal.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}