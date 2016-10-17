package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);
    private static final Comparator<Meal> MEAL_COMPARATOR = Comparator.comparing(Meal::getDateTime).reversed();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    public JdbcMealRepositoryImpl(DataSource dataSource) {
        this.jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("MEALS").usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal Meal, int userId) {
        Objects.requireNonNull(Meal);
        Objects.requireNonNull(userId);
        MapSqlParameterSource mapPS = new MapSqlParameterSource();
        mapPS.addValue("description", Meal.getDescription());
        mapPS.addValue("datetime", Meal.getDateTime());
        mapPS.addValue("calories", Meal.getCalories());
        mapPS.addValue("user_id", userId);
        if (Meal.isNew()) {
            Number newKey = jdbcInsert.executeAndReturnKey(mapPS);
            Meal.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update("UPDATE meals SET description=:description, datetime=:dateTime, " +
                    "calories=:calories WHERE id=:id AND user_id=:userId", mapPS);
        }
        return Meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_id=?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? ORDER BY id", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return getAll(userId).stream().filter(m-> TimeUtil.isBetween(m.getDateTime(),startDate, endDate)).collect(Collectors.toList());
    }
}
