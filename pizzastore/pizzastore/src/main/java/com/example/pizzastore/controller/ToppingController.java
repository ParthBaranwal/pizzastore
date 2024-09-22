package com.example.pizzastore.controller;

import com.example.pizzastore.model.Products;
import com.example.pizzastore.service.ToppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/toppings")
public class ToppingController {

    @Autowired
    private ToppingService toppingService;

    @PostMapping("/{toppingId}/add-to-pizza/{pizzaId}")
    public ResponseEntity<Void> addToppingToPizza(@PathVariable Long pizzaId, @PathVariable Long toppingId) {
        toppingService.addToppingToPizza(pizzaId, toppingId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
