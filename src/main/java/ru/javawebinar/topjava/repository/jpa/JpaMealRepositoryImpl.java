package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User user = entityManager.getReference(User.class, userId);
        if (meal.isNew()) {
            meal.setUser(user);
            entityManager.persist(meal);
            return meal;
        } else if (get(meal.getId(), userId) == null){
            return null;
        } else {
            meal.setUser(user);
            return entityManager.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return entityManager.createNamedQuery(Meal.DELETE).setParameter("id", id).setParameter("user_id", userId).executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        try {
            return entityManager.createNamedQuery(Meal.GET, Meal.class).setParameter("id", id).setParameter("user_id", userId).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return entityManager.createNamedQuery(Meal.ALL_SORTED, Meal.class).setParameter("user_id", userId).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return entityManager.createNamedQuery(Meal.GET_FILTERED, Meal.class).setParameter("user_id", userId).setParameter("startDate", startDate)
                .setParameter("endDate", endDate).getResultList();
    }
}