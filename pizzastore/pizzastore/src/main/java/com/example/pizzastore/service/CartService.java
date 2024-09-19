package com.example.pizzastore.service;

import com.example.pizzastore.dto.CartDTO;
import com.example.pizzastore.dto.CartItemDTO;
import com.example.pizzastore.dto.ProductDTO;
import com.example.pizzastore.model.*;
import com.example.pizzastore.repository.CartRepository;
import com.example.pizzastore.repository.ProductsRepository;
import com.example.pizzastore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private UserRepository userRepository;

    public CartDTO getCartById(Long cartId) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (!cartOpt.isPresent()) {
            throw new IllegalArgumentException("Cart not found");
        }

        Cart cart = cartOpt.get();

        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setTotalAmount(cart.getTotalAmount());

        List<CartItemDTO> cartItemDTOs = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setId(cartItem.getId());
            cartItemDTO.setQuantity(cartItem.getQuantity());
            cartItemDTO.setTotalPrice(cartItem.getTotalPrice());

            // Set product details
            ProductDTO productDTO = new ProductDTO();
            Products product = cartItem.getProduct();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setCategory(product.getCategory().toString());
            productDTO.setDescription(product.getDescription());

            cartItemDTO.setProduct(productDTO);

            // Conditionally set pizza and beverage related fields
            if (product.getCategory() == Category.PIZZA) {
                cartItemDTO.setPizzaSize(cartItem.getPizzaSize());
                cartItemDTO.setCrustType(cartItem.getCrustType());
            } else if (product.getCategory() == Category.BEVERAGE) {
                cartItemDTO.setBeverageSize(cartItem.getBeverageSize());
            }

            cartItemDTOs.add(cartItemDTO);
        }

        cartDTO.setCartItems(cartItemDTOs);

        return cartDTO;
    }

    public Cart createCartForUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        User user = userOpt.get();

        Cart cart = new Cart();
        cart.setUser(user);

        return cartRepository.save(cart);
    }

    public CartDTO addToCart(Long cartId, Long productId, int quantity, PizzaSize pizzaSize, CrustType crustType, BeverageSize beverageSize) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (!cartOpt.isPresent()) {
            throw new IllegalArgumentException("Cart not found");
        }

        Cart cart = cartOpt.get();
        Optional<Products> productOpt = productsRepository.findById(productId);
        if (productOpt.isPresent()) {
            Products product = productOpt.get();

            if (product.getCategory() == Category.PIZZA) {
                if (pizzaSize == null || crustType == null) {
                    throw new IllegalArgumentException("Pizza requires size and crust type to be specified");
                }
            } else if (product.getCategory() == Category.BEVERAGE) {
                if (beverageSize == null) {
                    throw new IllegalArgumentException("Beverage requires size to be specified");
                }
            }

            boolean itemExists = false;

            for (CartItem item : cart.getCartItems()) {
                if (item.getProduct().getId().equals(productId) &&
                        (product.getCategory() == Category.PIZZA && item.getPizzaSize() == pizzaSize && item.getCrustType() == crustType ||
                                product.getCategory() == Category.BEVERAGE && item.getBeverageSize() == beverageSize)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                    itemExists = true;
                    break;
                }
            }

            if (!itemExists) {
                CartItem newItem = new CartItem(product, quantity);
                newItem.setCart(cart);
                newItem.setPizzaSize(pizzaSize);
                newItem.setCrustType(crustType);
                newItem.setBeverageSize(beverageSize);
                cart.getCartItems().add(newItem);
            }

            // Update totalAmount
            cart.setTotalAmount(cart.getCartItems().stream()
                    .map(CartItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));

            cartRepository.save(cart);
        }

        // Convert Cart to CartDTO for the response
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setTotalAmount(cart.getTotalAmount());

        List<CartItemDTO> cartItemDTOs = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setId(cartItem.getId());
            cartItemDTO.setQuantity(cartItem.getQuantity());
            cartItemDTO.setTotalPrice(cartItem.getTotalPrice());

            // Set product details
            ProductDTO productDTO = new ProductDTO();
            Products product = cartItem.getProduct();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setCategory(product.getCategory().toString());
            productDTO.setDescription(product.getDescription());

            cartItemDTO.setProduct(productDTO);

            // Conditionally set pizza and beverage related fields
            if (product.getCategory() == Category.PIZZA) {
                cartItemDTO.setPizzaSize(cartItem.getPizzaSize());
                cartItemDTO.setCrustType(cartItem.getCrustType());
            } else if (product.getCategory() == Category.BEVERAGE) {
                cartItemDTO.setBeverageSize(cartItem.getBeverageSize());
            }

            cartItemDTOs.add(cartItemDTO);
        }

        cartDTO.setCartItems(cartItemDTOs);

        return cartDTO;
    }

    public Cart updateCart(Long cartId, Long productId, int quantity) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (!cartOpt.isPresent()) {
            throw new IllegalArgumentException("Cart not found");
        }

        Cart cart = cartOpt.get();
        Optional<CartItem> itemOpt = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (itemOpt.isPresent()) {
            CartItem item = itemOpt.get();
            item.setQuantity(quantity);
            item.setTotalPrice(item.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity)));
        } else {
            throw new IllegalArgumentException("Item not found in the cart");
        }

        // Recalculate the total amount
        cart.setTotalAmount(cart.getCartItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return cartRepository.save(cart);
    }

    public Cart removeFromCart(Long cartId, Long productId) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));

            cart.setCartItems(cart.getCartItems()); // Update totalAmount
            return cartRepository.save(cart);
        }

        throw new IllegalArgumentException("Cart not found");
    }

    public Cart clearCart(Long cartId) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            cart.getCartItems().clear(); // Remove all items from the cart
            cart.setTotalAmount(BigDecimal.ZERO); // Reset total amount to zero
            return cartRepository.save(cart);
        }
        throw new IllegalArgumentException("Cart not found");
    }
}
