package com.techyme.productservice.repository;

import com.techyme.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

//It is an interface use to generate a Mongodb repository for our model and the unique identification for that record
public interface ProductRepository extends MongoRepository<Product, String> {

}
