package com.example.pizzastore.controller;

import com.example.pizzastore.dto.CartDTO;
import com.example.pizzastore.dto.CreateCartRequest;
import com.example.pizzastore.dto.OrderDTO;
import com.example.pizzastore.model.Cart;
import com.example.pizzastore.model.CartItemRequest;
import com.example.pizzastore.model.Orders;
import com.example.pizzastore.model.User;
import com.example.pizzastore.repository.UserRepository;
import com.example.pizzastore.service.CartService;
import com.example.pizzastore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<CartDTO> createCartForUser(@RequestBody CreateCartRequest createCartRequest) {
        CartDTO cartDTO = cartService.createCartForUser(createCartRequest.getUserId());
        return ResponseEntity.ok(cartDTO);
    }


    @GetMapping("/myCart")
    public ResponseEntity<CartDTO> getCartByUser() {
        // Step 1: Get the currently authenticated user's username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Fetch the username from the JWT token or authentication

        // Step 2: Fetch the user by username from the database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Step 3: Fetch the cart by user
        CartDTO cartDTO = cartService.getCartByUser(user);

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
    @PostMapping("/checkout")
    public ResponseEntity<OrderDTO> checkout() {
        // Step 1: Get the currently authenticated user's username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username from authentication

        // Step 2: Call the order service to handle the checkout based on the user
        OrderDTO orderDTO = orderService.checkout(username);

        return ResponseEntity.ok(orderDTO);
    }
}
