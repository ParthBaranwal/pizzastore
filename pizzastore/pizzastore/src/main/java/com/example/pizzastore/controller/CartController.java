package com.example.pizzastore.controller;

import com.example.pizzastore.dto.CartDTO;
import com.example.pizzastore.model.Cart;
import com.example.pizzastore.model.CartItemRequest;
import com.example.pizzastore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long id) {
        CartDTO cartDTO = cartService.getCartById(id);
        return ResponseEntity.ok(cartDTO);
    }

    @PostMapping("/add")
    public Cart addToCart(@RequestBody CartItemRequest cartItemRequest) {
        return cartService.addToCart(cartItemRequest.getCartId(), cartItemRequest.getProductId(), cartItemRequest.getQuantity());
    }

    @PutMapping("/update")
    public Cart updateCart(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam int quantity) {
        return cartService.updateCart(cartId, productId, quantity);
    }

    @DeleteMapping("/remove")
    public Cart removeFromCart(@RequestParam Long cartId, @RequestParam Long productId) {
        return cartService.removeFromCart(cartId, productId);
    }
    @DeleteMapping("/clear")
    public Cart clearCart(@RequestParam Long cartId) {
        return cartService.clearCart(cartId);
    }
}
