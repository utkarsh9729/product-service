package com.ecom.productservice.service;

import com.ecom.productservice.dto.ProductRequest;
import com.ecom.productservice.dto.ProductResponse;
import com.ecom.productservice.model.Product;
import com.ecom.productservice.repository.ProductRepository;
import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {


    @Autowired
    ProductRepository productRepository;

    public Optional<ProductRequest>  saveProduct(ProductRequest productRequest){

        Product product = Product.builder().
                id(productRequest.getProductId()).
                name(productRequest.getProductName()).
                description(productRequest.getProductDescription()).
                price(productRequest.getProductPrice()).build();

        try{
          product =  productRepository.save(product);
          log.info("Saving Product Info");
        }
        catch (Exception e){
            if(e instanceof MongoException){
                return null;
            }
        }

        return  Optional.ofNullable(productRequest);
    }

    public Optional<ProductResponse> getProductById(String id){
        Optional<Product> product = productRepository.findById(id);
        log.info("retrieving Product Info");
        return Optional.ofNullable(response(product.get()));
    }

    public List<ProductResponse> findAllProducts(){

        List<Product> productList = productRepository.findAll();
        log.info("retrieving All Products Info");

       return productList.stream().map(product -> response(product)).toList();

    }


    public ProductResponse response(Product product){

        ProductResponse productResponse = ProductResponse.builder().
                productId(product.getId()).
                productDescription(product.getDescription()).
                productPrice(product.getPrice()).
                productName(product.getName()).build();
        return productResponse;
    }
}
