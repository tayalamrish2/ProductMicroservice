package org.lowes.MicroService.service;

import lowes.example.Product;
import org.lowes.MicroService.exception.ProductNotFoundException;
import org.lowes.MicroService.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    KafkaProducerService kafkaProducerService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product Id:" + id + " not found." );
        }
        return product.get();
    }

    //@Transactional stand alone mongo db does not support transactions
    public Product insertProduct(Product prod) {
        prod.setId(null);
        Product retVal = productRepository.insert(prod);
        kafkaProducerService.sendMessage(retVal);
        return retVal;
    }

    public Product updateProduct(Product prod) {
        if(productRepository.existsById(prod.getId())){
            Product retVal = productRepository.save(prod);
            kafkaProducerService.sendMessage(retVal);
            return retVal;
        }
        else
            throw new ProductNotFoundException("Product Id:" + prod.getId() + " not found." );
    }

    public Product patchProduct(Product prod) {
        Product val = this.getProduct(prod.getId());
        if(prod.getProductName() != null)
            val.setProductName(prod.getProductName());
        if(prod.getProductDescription() != null)
            val.setProductDescription(prod.getProductDescription());
        if(prod.getProductPrice() != null)
            val.setProductPrice(prod.getProductPrice());
        return this.updateProduct(val);
    }
}
