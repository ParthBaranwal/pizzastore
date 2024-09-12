package com.example.pizzastore.controller;

import com.example.pizzastore.dto.ProductResponseDTO;
import com.example.pizzastore.model.Category;
import com.example.pizzastore.model.Products;
import com.example.pizzastore.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping
    public List<Products> getAllProducts() {
        return productsService.getAllProducts();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Products> getProductById(@PathVariable Long id) {
//        return productsService.getProductById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Products product = productsService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));

        if (product.getCategory() == Category.PIZZA || product.getCategory() == Category.BEVERAGE) {
            // Call the service method to get product details
            ProductResponseDTO productResponse = productsService.getProductDetailsById(id);
            return ResponseEntity.ok(productResponse);
        } else {
            // Return the product directly for other categories
            return ResponseEntity.ok(product);
        }
    }

    @PostMapping
    public Products createProduct(@RequestBody Products product) {
        return productsService.saveProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable Long id, @RequestBody Products productDetails) {
        return ResponseEntity.ok(productsService.updateProduct(id, productDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productsService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // Retrieve all Pizzas
    @GetMapping("/pizzas")
    public ResponseEntity<List<Products>> getAllPizzas() {
        List<Products> pizzas = productsService.getProductsByCategory(Category.PIZZA);
        return new ResponseEntity<>(pizzas, HttpStatus.OK);

    }

    // Retrieve all Beverages
    @GetMapping("/beverages")
    public ResponseEntity<List<Products>> getAllBeverages() {
        List<Products> beverages = productsService.getProductsByCategory(Category.BEVERAGE);
        return new ResponseEntity<>(beverages, HttpStatus.OK);
    }

    // Retrieve all Sides
    @GetMapping("/sides")
    public ResponseEntity<List<Products>> getAllSides() {
        List<Products> sides = productsService.getProductsByCategory(Category.SIDE);
        return new ResponseEntity<>(sides, HttpStatus.OK);
    }

    // Retrieve all Toppings
    @GetMapping("/toppings")
    public ResponseEntity<List<Products>> getAllToppings() {
        List<Products> toppings = productsService.getProductsByCategory(Category.TOPPING);
        return new ResponseEntity<>(toppings, HttpStatus.OK);
    }
}
