package com.ecom.productservice.controller;

import com.ecom.productservice.dto.ProductRequest;
import com.ecom.productservice.dto.ProductResponse;
import com.ecom.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1")
public class ProductController {

    @Autowired
    ProductService productService;


    @PostMapping("/product")
    public ResponseEntity<ProductRequest> createProduct(@RequestBody ProductRequest productRequest){

        Optional<ProductRequest> tempProductRequest = productService.saveProduct(productRequest);
        if(Objects.nonNull(tempProductRequest.get())) {
            log.info("Product Saved");
            return new ResponseEntity<>(tempProductRequest.get(),HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable  String id){
        Optional<ProductResponse> productResponse = productService.getProductById(id);

        return new ResponseEntity<>(productResponse.get(),HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts(){

        List<ProductResponse> productResponses = productService.findAllProducts();
        return new ResponseEntity<>(productResponses,HttpStatus.OK);
    }


}
