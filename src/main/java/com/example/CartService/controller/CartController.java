package com.example.CartService.controller;

import com.example.CartService.dtos.AddProductToCartDto;
import com.example.CartService.dtos.CheckoutRequestDto;
import com.example.CartService.model.Cart;
import com.example.CartService.model.JwtObject;
import com.example.CartService.model.Product;
import com.example.CartService.service.CartService;
import com.example.CartService.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Cart/")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("addToCart")
    public ResponseEntity<String> addProductToCart(@RequestBody AddProductToCartDto addProductToCartDto
            ,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

//
        cartService.addToCart(addProductToCartDto.getQuantity(),
                addProductToCartDto.getProduct_id(),token);
        return ResponseEntity.ok("Product added to Cart");
    }

    @GetMapping("{id}")
    public ResponseEntity<Cart> cartReview(@PathVariable("id") Long cart_id) throws Exception {

        Cart cart = cartService.cartReview(cart_id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("checkout")
    public ResponseEntity<String> checkout(@RequestBody CheckoutRequestDto checkoutRequestDto) throws Exception {
        cartService.finalizePurchase(checkoutRequestDto.getCart_id());
        return ResponseEntity.ok("Purchase Finalised");
    }

    @GetMapping("/validate/{id}")
    public ResponseEntity<Cart> validateCart(@PathVariable ("id") Long cartId) throws Exception {
        Cart cart = cartService.validateCart(cartId);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cart);


    }
}
