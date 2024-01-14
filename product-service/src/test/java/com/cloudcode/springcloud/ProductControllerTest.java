package com.cloudcode.springcloud;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.cloudcode.springcloud.dao.AppDataJpaRepo;
import com.cloudcode.springcloud.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    private static final String AUTHORIZATION = "Authorization";

    private static final String CONTEXT_PATH = "/product-service";

    private static final String PRODUCT_SERVICE_V1_PATH = "/product-service/v1/product";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppDataJpaRepo appRepo;

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    private ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    private String token = null;

    private Product product = new Product(1L, "ABC", 99.99D);

    @BeforeEach
    public void beforeEach() {
        token = ("Basic " + new String(Base64.getEncoder().encode((username + ":" + password).getBytes())));
    }

    @Test
    public void test_getProducts() throws Exception {
        when(appRepo.findAll()).thenReturn(List.of(product));
        mockMvc.perform(get(PRODUCT_SERVICE_V1_PATH).contextPath(CONTEXT_PATH)
                .header(AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(content().json(objectWriter.writeValueAsString(List.of(product))))
                .andReturn();

    }

    @Test
    public void test_getProductById() throws Exception {
        when(appRepo.findById(product.getId())).thenReturn(Optional.of(product));
        mockMvc.perform(get(PRODUCT_SERVICE_V1_PATH + "/" + product.getId()).contextPath(CONTEXT_PATH)
                .header(AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(content().json(objectWriter.writeValueAsString(product)))
                .andReturn();
    }

    @Test
    public void test_saveProduct() throws Exception {
        when(appRepo.save(any())).thenReturn(product);
        mockMvc.perform(post(PRODUCT_SERVICE_V1_PATH).contextPath(CONTEXT_PATH)
                .contentType(MediaType.APPLICATION_JSON).content(objectWriter.writeValueAsString(product))
                .header(AUTHORIZATION, token))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectWriter.writeValueAsString(product)))
                .andReturn();
    }

    @Test
    public void test_deleteProductById() throws Exception {
        when(appRepo.existsById(product.getId())).thenReturn(true);
        doNothing().when(appRepo).deleteById(product.getId());
        mockMvc.perform(delete(PRODUCT_SERVICE_V1_PATH + "/" + product.getId()).contextPath(CONTEXT_PATH)
                .header(AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andReturn();
    }

}
