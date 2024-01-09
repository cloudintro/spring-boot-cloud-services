package com.cloudcode.springcloud;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FactoryServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FactoryServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void health_status_200_Ok() throws Exception {
        String responseBody = """
                {
                    "status": "UP"
                }
                """;
        MvcResult response = mockMvc.perform(get("/actuator/health")).andExpect(status().isOk())
                .andExpect(content().json(responseBody)).andReturn();
        String status = (String) new ObjectMapper().readValue(response.getResponse().getContentAsString(), Map.class)
                .get("status");
        assertEquals("UP", status);
    }

}
