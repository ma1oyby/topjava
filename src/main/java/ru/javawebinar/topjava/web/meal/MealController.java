package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by BOSS on 02.11.2016.
 */
@Controller
@RequestMapping(value = "/meals")
public class MealController extends AbstractMealController {

    @RequestMapping(method = RequestMethod.GET)
    public String meals(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute("meal", super.get(Integer.parseInt(request.getParameter("id"))));
        return "meal";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request) {
        super.delete(Integer.parseInt(request.getParameter("id")));
        return "redirect:/meals";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now(), "", 1000));
        return "meal";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createOrUpdate(HttpServletRequest request, Model model) {
        String mealId = request.getParameter("id");
        if (mealId.isEmpty()) {
            Meal meal = new Meal(null, LocalDateTime.parse(request.getParameter("dateTime")), request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
            super.create(meal);
        } else {
            Meal meal = new Meal(Integer.parseInt(mealId), LocalDateTime.parse(request.getParameter("dateTime")), request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
            super.update(meal, Integer.parseInt(mealId));
        }
        return "redirect:/meals";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filter(HttpServletRequest request, Model model) {
        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
        LocalTime startTime = LocalTime.parse(request.getParameter("startTime"));
        LocalTime endTime = LocalTime.parse(request.getParameter("endTime"));
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }
}

