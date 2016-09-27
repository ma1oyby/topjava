package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by BOSS on 17.09.2016.
 */
public class MealServlet extends HttpServlet {

    private static final Logger LOG = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to MealServlet");
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("mealList", MealsUtil.getWithExceeded(MealsUtil.MEAL_LIST, 2000));
        req.getRequestDispatcher("/mealList.jsp").forward(req, resp);
    }
}
