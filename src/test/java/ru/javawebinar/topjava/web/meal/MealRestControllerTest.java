package ru.javawebinar.topjava.web.meal;

import org.junit.Test;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;


import java.time.Month;

import static java.time.LocalDateTime.of;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;

public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_MEAL_URL = MealRestController.REST_MEAL_URL + "/";

    @Test
    public void getAll() throws Exception {
        TestUtil.print(mockMvc.perform(MockMvcRequestBuilders.get(REST_MEAL_URL)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MATCHER_EXCEED.contentListMatcher(MealsUtil.getWithExceeded(MEALS, 2000)));
    }

    @Test
    public void get() throws Exception {
        TestUtil.print(mockMvc.perform(MockMvcRequestBuilders.get(REST_MEAL_URL+MEAL1_ID)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void delete() throws Exception {
        TestUtil.print(mockMvc.perform(MockMvcRequestBuilders.get(REST_MEAL_URL))
                .andExpect(status().isOk()));
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL1_ID, MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories());
        updated.setCalories(200);
        updated.setDescription("Обновленный завтрак");
        mockMvc.perform(put(REST_MEAL_URL+MEAL1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, getUpdated());
    }

    @Test
    public void create() throws Exception {
        Meal created = new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "Созданный ужин", 300);
        mockMvc.perform(post(REST_MEAL_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))).andExpect(status().isCreated());
        MATCHER.assertEquals(created, getCreated());
    }
}