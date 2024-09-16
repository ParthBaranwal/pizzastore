package com.example.pizzastore.service;

import com.example.pizzastore.dto.CartDTO;
import com.example.pizzastore.dto.CartItemDTO;
import com.example.pizzastore.dto.ProductDTO;
import com.example.pizzastore.model.Cart;
import com.example.pizzastore.model.CartItem;
import com.example.pizzastore.model.Products;
import com.example.pizzastore.repository.CartRepository;
import com.example.pizzastore.repository.ProductsRepository;
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


    public CartDTO getCartById(Long cartId) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (!cartOpt.isPresent()) {
            // Handle cart not found
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
            cartItemDTOs.add(cartItemDTO);
        }

        cartDTO.setCartItems(cartItemDTOs);

        return cartDTO;
    }

    public Cart addToCart(Long cartId, Long productId, int quantity) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        Cart cart = cartOpt.orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setCartItems(new ArrayList<>());
            return newCart;
        });

        Optional<Products> productOpt = productsRepository.findById(productId);
        if (productOpt.isPresent()) {
            Products product = productOpt.get();
            boolean productExists = false;

            if (cart.getCartItems() == null) {
                cart.setCartItems(new ArrayList<>());
            }

            for (CartItem item : cart.getCartItems()) {
                if (item.getProduct().getId().equals(productId)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                    productExists = true;
                    break;
                }
            }

            if (!productExists) {
                CartItem newItem = new CartItem(product, quantity);
                newItem.setCart(cart);
                cart.getCartItems().add(newItem);
            }

            cart.setCartItems(cart.getCartItems()); // Update totalAmount
            cartRepository.save(cart);
        }

        return cart;
    }

    public Cart updateCart(Long cartId, Long productId, int quantity) {
        return addToCart(cartId, productId, quantity);
    }

    public Cart removeFromCart(Long cartId, Long productId) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId) && item.getQuantity() <= 1);

            cart.setCartItems(cart.getCartItems()); // Update totalAmount
            return cartRepository.save(cart);
        }

        return null;
    }
    public Cart clearCart(Long cartId) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            cart.getCartItems().clear(); // Remove all items from the cart
            cart.setTotalAmount(BigDecimal.ZERO); // Reset total amount to zero
            return cartRepository.save(cart);
        }
        return null;
    }
}
