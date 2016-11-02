package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {
    @Autowired
    private UserService service;

    @Autowired
    private MealService mealService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model) {
        model.addAttribute("users", service.getAll());
        return "users";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        return "redirect:meals";
    }

//    @RequestMapping(value = "/meals", method = RequestMethod.GET)
//    public String meals(Model model) {
//        model.addAttribute("meals", MealsUtil.getWithExceeded(mealService.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()));
//        return "meals";
//    }
//
//    @RequestMapping(value = "/meals/update", method = RequestMethod.GET)
//    public String update (HttpServletRequest request, Model model) {
//        int mealId = Integer.parseInt(request.getParameter("id"));
//        model.addAttribute("meal", mealService.get(mealId, AuthorizedUser.id()));
//        return "meal";
//    }
//
//    @RequestMapping(value = "/meals/delete", method = RequestMethod.GET)
//    public String delete(HttpServletRequest request) {
//        int mealId = Integer.parseInt(request.getParameter("id"));
//        mealService.delete(mealId, AuthorizedUser.id());
//        return "redirect:/meals";
//    }
}
