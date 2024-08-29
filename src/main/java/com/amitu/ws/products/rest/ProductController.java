package com.amitu.ws.products.rest;

import com.amitu.ws.products.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody CreateProductsRestModel product){
        //Asynchronous
        //String productId = productService.createProductAsync(product);

        //Synchronous
        String productId = null;
        try {
            productId = productService.createProductSync(product);
        } catch (Exception e) {
            LOGGER.error("Exception : {}",e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorMessage(new Date(), e.getMessage(),"/products"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }
}
