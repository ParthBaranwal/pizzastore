package com.example.pizzastore.service;

import com.example.pizzastore.exception.ProductNotFoundException;
import com.example.pizzastore.model.Category;
import com.example.pizzastore.model.Products;
import com.example.pizzastore.model.PizzaTopping;
import com.example.pizzastore.repository.ProductsRepository;
import com.example.pizzastore.repository.PizzaToppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ToppingService {

    @Autowired
    private ProductsRepository productRepository;

    @Autowired
    private PizzaToppingRepository pizzaToppingRepository;


    public void addToppingToPizza(Long pizzaId, Long toppingId) {
        Products pizza = productRepository.findById(pizzaId)
                .orElseThrow(() -> new ProductNotFoundException("Pizza not found"));

        Products topping = productRepository.findById(toppingId)
                .orElseThrow(() -> new ProductNotFoundException("Topping not found"));

        if (pizza.getCategory() != Category.PIZZA) {
            throw new IllegalArgumentException("The product is not a pizza.");
        }

        if (topping.getCategory() != Category.TOPPING) {
            throw new IllegalArgumentException("The product is not a topping.");
        }

        PizzaTopping pizzaTopping = new PizzaTopping();
        pizzaTopping.setPizza(pizza);
        pizzaTopping.setTopping(topping);

        pizzaToppingRepository.save(pizzaTopping);
    }
}
