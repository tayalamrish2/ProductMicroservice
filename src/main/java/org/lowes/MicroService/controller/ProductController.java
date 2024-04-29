package org.lowes.MicroService.controller;

import lowes.example.Product;
import org.lowes.MicroService.exception.ProductNotFoundException;
import org.lowes.MicroService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public Product getProduct(@RequestParam String id) {
        return productService.getProduct(id);
    }

    @GetMapping("/allProducts")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Product insertProduct(@RequestBody Product prod){
        return productService.insertProduct(prod);
    }

    @PatchMapping
    public Product patchProduct(@RequestBody Product prod) {
        return productService.patchProduct(prod);
    }

}
