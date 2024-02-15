package com.techyme.productservice.Service;


import com.techyme.productservice.dto.ProductRequest;
import com.techyme.productservice.dto.ProductResponse;
import com.techyme.productservice.model.Product;
import com.techyme.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//This is the main class where we build our apis request and response body.
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    //injecting repository in the service class
    private final ProductRepository productRepository;

//    public ProductService(ProductRepository productRepository){
//        this.productRepository=productRepository;
//    }
    //CreateProduct is a function that will be called in the controller in correspondense to a particular endpoint.
    public void createProduct(ProductRequest productRequest){
        Product product= Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
    
        //Saving the product in the database.
        productRepository.save(product);

        log.info("Product {} is saved", product.getId());
        

    }

    //getAllProducts is a function that corresponses to a get request api endpoint.
    public List<ProductResponse> getAllProducts(){
        List <Product> products=productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
