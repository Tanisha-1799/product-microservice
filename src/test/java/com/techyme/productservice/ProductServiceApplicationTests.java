package com.techyme.productservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
class ProductServiceApplicationTests {

    //Since we are not using local mongodb so making an image with the desired version of mongodb
    //to execute the integration test.
    @Container
	static MongoDBContainer mongoDBContainer=new MongoDBContainer("mongo: 4.4.2");


    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry){
        dymDynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);

    }

    //creating integration test to test the functionality of creating a request.
	@Test
	void contextLoads() {
	}
	//Using "testcontainers" are used for integration tests, a java library.
}
