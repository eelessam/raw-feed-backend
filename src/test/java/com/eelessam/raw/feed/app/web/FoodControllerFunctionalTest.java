package com.eelessam.raw.feed.app.web;

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
    public void create_withValidFoodPayload_shouldReturn201() throws Exception {
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getJsonFixture("fixtures/validFoodPayload.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void create_withMalformedJson_shouldReturn400() throws Exception {
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getJsonFixture("fixtures/malformedFoodPayload.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_withWrongContentType_shouldReturn415() throws Exception {
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getJsonFixture("fixtures/validFoodPayload.json"))
                .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void create_withMissingFoodPropertyFromFoodPayload_shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getJsonFixture("fixtures/missingFoodFromPayload.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_withMissingBonePropertyFromFoodPayload_shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getJsonFixture("fixtures/missingBonePercentageFromPayload.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_withMissingOffalPropertyFromFoodPayload_shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getJsonFixture("fixtures/missingOffalPercentageFromPayload.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_withMissingMeatPropertyFromFoodPayload_shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(post(CONTROLLER_PATH)
                .content(getJsonFixture("fixtures/missingMeatPercentageFromPayload.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private String getJsonFixture(String filePath) throws URISyntaxException, IOException {
        return new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(filePath).toURI())));
    }

}