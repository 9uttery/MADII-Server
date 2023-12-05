package com.guttery.madii;

import com.guttery.madii.common.config.security.SecurityConfig;
import com.guttery.madii.controller.HealthController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
	void healthCheck() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/health")
						.accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
				.andExpect(content().string("healthy"));
	}


	@Test
	@DisplayName("DTO 반환이 ApiResponseBodyAdvice에서 잘 매핑되는지 테스트")
	void dtoCheck() throws Exception {
		mockMvc.perform(
						MockMvcRequestBuilders.get("/health/dto")
								.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(200))
				.andExpect(jsonPath("$.code").isNotEmpty())
				.andExpect(jsonPath("$.message").value("API 요청이 성공했습니다."))
				.andExpect(jsonPath("$.data.status").value("healthy"))
				.andExpect(jsonPath("$.timestamp", Matchers.matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d+"))); // 정규표현식을 사용하여 timestamp 형식 검증
	}

	@Test
	@DisplayName("에러를 throw했을 때 Advice들에 의해 잘 매핑되는지 테스트")
	void errorCheck() throws Exception {
		mockMvc.perform(
						MockMvcRequestBuilders.get("/health/error")
								.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.code").exists())
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.timestamp", Matchers.matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d+"))); // 정규표현식을 사용하여 timestamp 형식 검증
	}
}
