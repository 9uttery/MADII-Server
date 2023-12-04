package com.guttery.madii;

import com.guttery.madii.common.config.security.SecurityConfig;
import com.guttery.madii.controller.HealthController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
		controllers = HealthController.class
)
@Import(SecurityConfig.class)
class MadiiApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("서버 시작 후 Health Check API를 테스트")
	void contextLoads() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/health")
						.accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
				.andExpect(content().string("healthy"));
	}

}
