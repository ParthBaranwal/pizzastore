package com.example.pizzastore.service;

import com.example.pizzastore.dto.ProductResponseDTO;
import com.example.pizzastore.dto.ToppingDTO;
import com.example.pizzastore.model.*;
import com.example.pizzastore.repository.PizzaToppingRepository;
import com.example.pizzastore.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private PizzaToppingRepository pizzaToppingRepository;

    public List<Products> getAllProducts() {

        return productsRepository.findAll()
                .stream()
                .filter(product -> product.getCategory() != Category.TOPPING)
                .collect(Collectors.toList());
    }

    public Optional<Products> getProductById(Long id) {

        Optional<Products> product = productsRepository.findById(id);

        // Check if the product is a topping and return empty if true
        if (product.isPresent() && product.get().getCategory() == Category.TOPPING) {
            return Optional.empty();  // Return an empty optional if it's a topping
        }

        return product;
    }

    public Products saveProduct(Products product) {

        return productsRepository.save(product);
    }

    public Products updateProduct(Long id, Products productDetails) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setCategory(productDetails.getCategory());
        product.setDescription(productDetails.getDescription());
        return productsRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productsRepository.deleteById(id);
    }

    // Method to retrieve products by category
    public List<Products> getProductsByCategory(Category category) {
        return productsRepository.findByCategory(category);
    }

    public ProductResponseDTO getProductDetailsById(Long id) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));

        if (product.getCategory() == Category.PIZZA) {

//            // Retrieve toppings
//            List<Products> toppings = productsRepository.findByCategory(Category.TOPPING);
//
//            // Convert toppings to ToppingDTO
//            List<ToppingDTO> toppingDTOs = toppings.stream()
//                    .map(topping -> new ToppingDTO(topping.getId(), topping.getName(), topping.getPrice(), topping.getDescription()))
//                    .collect(Collectors.toList());

            // Retrieve toppings associated with the pizza
            List<PizzaTopping> pizzaToppings = pizzaToppingRepository.findByPizza(product);

            // Convert toppings to ToppingDTO
            List<ToppingDTO> toppingDTOs = pizzaToppings.stream()
                    .map(pizzaTopping -> {
                        Products topping = pizzaTopping.getTopping();
                        return new ToppingDTO(topping.getId(), topping.getName(), topping.getPrice(), topping.getDescription());
                    })
                    .collect(Collectors.toList());

            // Create response DTO for pizza
            return new ProductResponseDTO(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getDescription(),
                    Arrays.asList(PizzaSize.values()), // Available pizza sizes
                    Arrays.asList(CrustType.values()),  // Available crust types
                    toppingDTOs
            );
        } else if (product.getCategory() == Category.BEVERAGE) {
            // Create response DTO for beverage
            return new ProductResponseDTO(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getDescription(),
                    Arrays.asList(BeverageSize.values())  // Available beverage sizes
            );
        } else {
            throw new RuntimeException("Product is not a pizza or beverage");
        }
    }
}
