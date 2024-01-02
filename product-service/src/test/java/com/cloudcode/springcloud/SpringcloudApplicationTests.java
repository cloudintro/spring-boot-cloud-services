package com.cloudcode.springcloud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = SpringCloudApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SpringCloudApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Value("${spring.security.user.name}")
	private String username;

	@Value("${spring.security.user.password}")
	private String password;

	@Value("${spring.application.name}")
	private String appname;


	@Test
	public void health_status_200_Ok() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + new String(Base64.getEncoder().encode((username + ":" + password).getBytes())));
		MockHttpServletRequestBuilder request = get("/health").headers(headers);
		MvcResult response = mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		String name = (String)new ObjectMapper().readValue(response.getResponse().getContentAsString(), Map.class).get("name");
		assertEquals(appname, name);
	}

	@Test
	public void health_status_401_Unauthorized() throws Exception {
		mockMvc.perform(get("/health")).andExpect(status().isUnauthorized());
	}

}
