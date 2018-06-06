package com.eelessam.raw.feed.app;

import com.eelessam.raw.feed.app.web.FoodController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RawFeedApplicationTests {

	@Autowired
	private FoodController foodController;

	@Test
	public void contextLoads() {
		assertThat(foodController).isNotNull();
	}

}
