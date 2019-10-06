package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.TestDataMeals;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MapMealStorage;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private MealStorage storage;
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    public void init() throws ServletException {
        storage = new MapMealStorage();
        TestDataMeals.fillStorage(storage);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String dateTime = request.getParameter("date-time");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        if ("".equals(id)) {
            storage.save(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            storage.update(meal);
        }
        log.debug("redirect to meals");
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            mealsForward(request, response);
            return;
        }
        String id = request.getParameter("id");

        Meal meal = null;
        switch (action) {
            case "delete":
                storage.delete(Integer.parseInt(id));
                log.debug("redirect to meals");
                response.sendRedirect("meals");
                return;
            case "edit":
            case "add":
                if (id != null) {
                    meal = storage.get(Integer.parseInt(id));
                }
                break;
            default:
                mealsForward(request, response);
                return;
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
    }

    private void mealsForward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("meals", MealsUtil.getFiltered(storage.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

}
