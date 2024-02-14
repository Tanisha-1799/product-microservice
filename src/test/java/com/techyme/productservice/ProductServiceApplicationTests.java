package com.techyme.productservice;

import com.techyme.productservice.dto.ProductRequest;
import com.techyme.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

    //Since we are not using local mongodb so making an image with the desired version of mongodb
    //to execute the integration test.
    @Container
	static MongoDBContainer mongoDBContainer=new MongoDBContainer("mongo: 4.4.2");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    //object mapper converts the pojo object to json and json to pojo object.
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry){
        dymDynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);

    }

    //creating integration test to test the functionality of creating a request.
	@Test
	void shouldCreateProduct() throws Exception {

        ProductRequest productRequest=getProductRequest();
        String productRequestString=objectMapper.writeValueAsString(productRequest);

        //providing a mock servlet environment where we can mock our controllers endpoint and
        //check that correct response is received or not
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestString))    //Since content method can take only a string value so we have to change the productRequest variable value to string
                .andExpect(status().isCreated());  //Now checking for response if the expected response is there or not

        Assertions.assertEquals(1, productRepository.findAll().size());
	}
	//Using "testcontainers" are used for integration tests, a java library.

    private ProductRequest getProductRequest(){
        return ProductRequest.builder()
                .name("iphone 13")
                .description("iphone 13")
                .price(BigDecimal.valueOf(1200))
                .build();
    }
}
