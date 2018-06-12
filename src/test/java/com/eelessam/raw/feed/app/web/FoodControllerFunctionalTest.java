package com.eelessam.raw.feed.app.web;

import com.eelessam.raw.feed.app.domain.Food;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FoodControllerFunctionalTest {

    private static String CONTROLLER_PATH = "/food";

    private static ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void get_foodId_shouldReturn200() throws Exception {
        URI path = new DefaultUriBuilderFactory().uriString(CONTROLLER_PATH).queryParam("foodId", 12345).build();

        this.mockMvc.perform(get(path))
                .andExpect(status().isOk());
    }

    @Test
    public void get_withoutFoodId_shouldNotReturn200() throws Exception {
        this.mockMvc.perform(get(CONTROLLER_PATH))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void get_withNonNumericFoodId_shouldNotReturn200() throws Exception {
        URI path = new DefaultUriBuilderFactory().uriString(CONTROLLER_PATH).queryParam("foodId", "fsdd").build();

        this.mockMvc.perform(get(path))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void post_withValidFoodPayload_shouldReturn201() throws Exception {
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getJsonFixture("fixtures/validFoodPayload.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void post_withMalformedJson_shouldReturn400() throws Exception {
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getJsonFixture("fixtures/malformedFoodPayload.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void post_withWrongContentType_shouldReturn415() throws Exception {
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getJsonFixture("fixtures/validFoodPayload.json"))
                .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void post_withMissingFoodProperty_shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getJsonFixture("fixtures/missingFoodFromPayload.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void post_withMissingBoneProperty_shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getJsonFixture("fixtures/missingBonePercentageFromPayload.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void post_withMissingOffalProperty_shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getJsonFixture("fixtures/missingOffalPercentageFromPayload.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void post_withMissingMeatProperty_shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getJsonFixture("fixtures/missingMeatPercentageFromPayload.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void post_withNullBoneProperty_shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getJsonFixture("fixtures/nullBonePercentagePayload.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void post_withNullOffalProperty_shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getJsonFixture("fixtures/nullOffalPercentagePayload.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void post_withNullMeatProperty_shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getJsonFixture("fixtures/nullMeatPercentagePayload.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void post_withNegativeNumberForBoneProperty_shouldReturnBadRequest() throws Exception {

        // given
        Food food = Food.builder()
                .name("Test food")
                .bonePercentage(-1)
                .meatPercentage(10)
                .offalPercentage(10)
                .build();

        // when then
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getFoodJson(food))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void post_withNegativeNumberForOffalProperty_shouldReturnBadRequest() throws Exception {

        // given
        Food food = Food.builder()
                .name("Test food")
                .bonePercentage(10)
                .meatPercentage(10)
                .offalPercentage(-1)
                .build();

        // when then
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getFoodJson(food))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void post_withNegativeNumberForMeatProperty_shouldReturnBadRequest() throws Exception {

        // given
        Food food = Food.builder()
                .name("Test food")
                .bonePercentage(50)
                .meatPercentage(-25)
                .offalPercentage(24)
                .build();

        // when then
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getFoodJson(food))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void post_withPercentageTotalEquallingLessThan100_shouldReturnBadRequest() throws Exception {

        // given
        Food food = Food.builder()
                .name("Test food")
                .bonePercentage(0)
                .meatPercentage(24)
                .offalPercentage(75)
                .build();

        // when then
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getFoodJson(food))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void post_withPercentageTotalEquallingMoreThan100_shouldReturnBadRequest() throws Exception {

        // given
        Food food = Food.builder()
                .name("Test food")
                .bonePercentage(99)
                .meatPercentage(1)
                .offalPercentage(1)
                .build();

        // when then
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getFoodJson(food))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private String getFoodJson(Food food) throws JsonProcessingException {
        return objectMapper.writeValueAsString(food);
    }

    private String getJsonFixture(String filePath) throws URISyntaxException, IOException {
        return new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(filePath).toURI())));
    }

}