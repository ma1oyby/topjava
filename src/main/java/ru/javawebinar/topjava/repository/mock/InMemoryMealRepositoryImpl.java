package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
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
        //MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Integer mealId = meal.getId();
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else if (get(mealId, userId) == null)
            return null;
        meal.setUserId(userId);
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal.getUserId() == userId)
            return repository.remove(id) != null;
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        for (Map.Entry<Integer, Meal> pair : repository.entrySet()  ) {
            Meal currentMeal = pair.getValue();
            if (currentMeal.getUserId() == userId)
                return currentMeal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream().filter(m->(m.getUserId()== userId)).sorted((m1,m2)->m2.getDateTime().compareTo(m1.getDateTime())).collect(Collectors.toList());
    }
}

