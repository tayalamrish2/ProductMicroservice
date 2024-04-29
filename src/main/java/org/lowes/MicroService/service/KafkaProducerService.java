package org.lowes.MicroService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lowes.example.Product;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.lowes.MicroService.exception.ProductUpdateFailed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {
    @Value("${app.kafka.product.topic}")
    private String TOPIC_NAME;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    ObjectMapper mapper = new ObjectMapper();

    public void sendMessage(Product product) {
        try {
            String jsonString = mapper.writeValueAsString(product);
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, jsonString);
            System.out.println("Kafka send msg sending: " + jsonString);
            kafkaTemplate.send(record).exceptionally((res) -> {
                System.out.println("Kafka send msg failed: " + record);
                System.out.println(res.getMessage());
                return null;
            });
        } catch (JsonProcessingException e) {
            throw new ProductUpdateFailed("Product could not be published.");
        }
    }
}
