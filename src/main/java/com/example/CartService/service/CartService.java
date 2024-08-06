package com.example.CartService.service;

import com.example.CartService.client.RestClient;
//import com.example.CartService.config.KafkaProducerConfig;
import com.example.CartService.dtos.SendEmailDto;
import com.example.CartService.model.*;
import com.example.CartService.repository.CartRepository;
import com.example.CartService.repository.ProductRepository;
import com.example.CartService.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ProductRepository productRepository;

    public void addToCart(int quantity, Long product_id, String token) {
        Product product = RestClient.getProduct(product_id,token);
        productRepository.save(product);

        JwtObject jwtObject = jwtUtil.convertTokenToJwtObject(token);
        Long userId = jwtObject.getUserId();

        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart);
        cart.setUser_id(userId);
        cart.setCartItemList( List.of(cartItem));

        cartRepository.save(cart);
    }

    public Cart cartReview(Long cart_id) throws Exception {
        Optional<Cart> cartOptional = cartRepository.findById(cart_id);
        if (cartOptional.isEmpty()) {
            throw new Exception("Cart not found");
        }
        return cartOptional.get();
    }

    public void finalizePurchase(Long Cart_id) throws Exception {

        Optional<Cart> cartOptional = cartRepository.findById(Cart_id);
        if (cartOptional.isEmpty()) {
            throw new Exception("Cart not found");
        }
        Cart cart = cartOptional.get();

//        kafkaProducerConfig.sendMessage("placeOrder", objectMapper.writeValueAsString(cart));
         RestClient.sendCart(cart);

    }

    public Cart validateCart(Long cartId) throws Exception {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isEmpty()) {
            return null;
        }
        return cartOptional.get();
    }
}


