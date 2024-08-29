package com.amitu.ws.products.service;

import com.amitu.ws.products.rest.CreateProductsRestModel;

import java.util.concurrent.ExecutionException;

public interface ProductService {

    String createProductAsync(CreateProductsRestModel productsRestModel);

    String createProductSync(CreateProductsRestModel productsRestModel) throws Exception;
}
