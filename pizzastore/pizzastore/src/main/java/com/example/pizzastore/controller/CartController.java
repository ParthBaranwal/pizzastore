package com.example.pizzastore.controller;

import com.example.pizzastore.dto.CartDTO;
import com.example.pizzastore.dto.CreateCartRequest;
import com.example.pizzastore.model.Cart;
import com.example.pizzastore.model.CartItemRequest;
import com.example.pizzastore.model.Orders;
import com.example.pizzastore.service.CartService;
import com.example.pizzastore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Cart> createCartForUser(@RequestBody CreateCartRequest createCartRequest) {
        Cart cart = cartService.createCartForUser(createCartRequest.getUserId());
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long cartId) {
        CartDTO cartDTO = cartService.getCartById(cartId);
        return ResponseEntity.ok(cartDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<CartDTO> addToCart(@RequestBody CartItemRequest cartItemRequest) {
        CartDTO cartDTO = cartService.addToCart(
                cartItemRequest.getCartId(),
                cartItemRequest.getProductId(),
                cartItemRequest.getQuantity(),
                cartItemRequest.getPizzaSize(),
                cartItemRequest.getCrustType(),
                cartItemRequest.getBeverageSize()
        );
        return ResponseEntity.ok(cartDTO);
    }


    @PutMapping("/update")
    public ResponseEntity<Cart> updateCart(@RequestBody CartItemRequest cartItemRequest) {
        Cart cart = cartService.updateCart(cartItemRequest.getCartId(), cartItemRequest.getProductId(), cartItemRequest.getQuantity());
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Cart> removeFromCart(@RequestParam Long cartId, @RequestParam Long productId) {
        Cart cart = cartService.removeFromCart(cartId, productId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Cart> clearCart(@RequestParam Long cartId) {
        Cart cart = cartService.clearCart(cartId);
        return ResponseEntity.ok(cart);


    }
    @PostMapping("/checkout/{cartId}")
    public ResponseEntity<Orders> checkout(@PathVariable Long cartId) {
        Orders order = orderService.checkout(cartId);
        return ResponseEntity.ok(order);
    }
}
