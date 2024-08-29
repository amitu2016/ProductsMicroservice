package com.amitu.ws.products.service;

import com.amitu.ws.products.rest.CreateProductsRestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ProductServiceImpl implements ProductService{

    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProductAsync(CreateProductsRestModel productsRestModel) {
        String productId = UUID.randomUUID().toString();
        // TODO: persist data into database

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId,
                productsRestModel.getTitle(), productsRestModel.getPrice(),
                productsRestModel.getQuantity());

        CompletableFuture<SendResult<String, ProductCreatedEvent>> future = kafkaTemplate.send("product-created-events-topic", productId, productCreatedEvent);

        future.whenComplete((result, exception) -> {
            if(exception != null){
                LOGGER.error("******* Failed to send message: {}", exception.getMessage());
            }else{
                LOGGER.info("******* Message was sent successfully: {}",result.getRecordMetadata());
            }
        });

        LOGGER.info("****** Returning the product id : {}",productId);
        return productId;
    }

    @Override
    public String createProductSync(CreateProductsRestModel productsRestModel) throws Exception{
        String productId = UUID.randomUUID().toString();
        // TODO: persist data into database

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId,
                productsRestModel.getTitle(), productsRestModel.getPrice(),
                productsRestModel.getQuantity());

        LOGGER.info("****** Before publishing productCreatedEvent");
        SendResult<String, ProductCreatedEvent> result = kafkaTemplate.send("product-created-events-topic", productId, productCreatedEvent).get();

        LOGGER.info("Partition : {}", result.getRecordMetadata().partition());
        LOGGER.info("Topic : {}", result.getRecordMetadata().topic());
        LOGGER.info("Offset : {}", result.getRecordMetadata().offset());

        LOGGER.info("****** Returning the product id : {}",productId);
        return productId;
    }
}
