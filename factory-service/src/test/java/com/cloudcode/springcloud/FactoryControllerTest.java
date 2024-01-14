package com.cloudcode.springcloud;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.cloudcode.springcloud.client.ProductServiceProxy;
import com.cloudcode.springcloud.dao.AppDataJpaRepo;
import com.cloudcode.springcloud.model.Factory;
import com.cloudcode.springcloud.model.FactoryResponse;
import com.cloudcode.springcloud.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc
public class FactoryControllerTest {

    private static final String CONTEXT_PATH = "/factory-service";

    private static final String END_POINT_V1_PATH = "/factory-service/v1/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppDataJpaRepo appRepo;

    @MockBean
    private ProductServiceProxy productService;

    private ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    private Product product = new Product("ABC", 99.99D);
    private Factory factory = new Factory("ABC", Set.of("prod01"));
    private FactoryResponse factoryResponse = new FactoryResponse(factory.getFactoryName(), List.of(product));

    @Test
    public void test_getProducts() throws Exception {
        when(appRepo.findById(factory.getFactoryName())).thenReturn(Optional.of(factory));
        when(productService.getProducts(anyMap())).thenReturn(new ResponseEntity<>(List.of(product), HttpStatus.OK));

        mockMvc.perform(get(END_POINT_V1_PATH + factory.getFactoryName() + "/products").contextPath(CONTEXT_PATH))
                .andExpect(status().isOk())
                .andExpect(content().json(objectWriter.writeValueAsString(factoryResponse)))
                .andReturn();
    }

    @Test
    public void test_createFactory() throws Exception {
        when(appRepo.findById(any())).thenReturn(Optional.empty());
        when(appRepo.save(any())).thenReturn(factory);

        mockMvc.perform(post(END_POINT_V1_PATH + "factory/" + factory.getFactoryName()).contextPath(CONTEXT_PATH))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectWriter.writeValueAsString(factory)))
                .andReturn();
    }

    @Test
    public void test_createProduct() throws Exception {
        factory.setProductNames(null);
        when(appRepo.findById(any())).thenReturn(Optional.of(factory));
        when(appRepo.save(any())).thenReturn(factory);
        when(productService.saveProduct(anyMap(), any())).thenReturn(new ResponseEntity<>(product, HttpStatus.OK));
        mockMvc.perform(post(END_POINT_V1_PATH + factory.getFactoryName() + "/product").contextPath(CONTEXT_PATH)
                .contentType(MediaType.APPLICATION_JSON).content(objectWriter.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectWriter.writeValueAsString(factoryResponse)))
                .andReturn();
    }

}
