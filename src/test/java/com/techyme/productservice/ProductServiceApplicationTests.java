package com.techyme.productservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class ProductServiceApplicationTests {

	MongoDBContainer mongoDBContainer=new MongoDBContainer();

	@Test
	void contextLoads() {
	}
	//Using "testcontainers" are used for integration tests, a java library.
}
