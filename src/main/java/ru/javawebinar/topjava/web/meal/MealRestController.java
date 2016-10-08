package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    @Autowired
    private MealService service;

    public Meal save(Meal meal) {
        int userId = AuthorizedUser.id();
        return service.save(meal, userId);
    }

    public Meal get(int id) {
        int userId = AuthorizedUser.id();
        return service.get(id, userId);
    }

    public void delete (int id) {
        int userId = AuthorizedUser.id();
        service.delete(id, userId);
    }

    public List<Meal> getAll() {
        int userId = AuthorizedUser.id();
        return service.getAll(userId);
    }

    public void update(Meal meal) {
        int userId = AuthorizedUser.id();
        service.update(meal, userId);
    }

}
