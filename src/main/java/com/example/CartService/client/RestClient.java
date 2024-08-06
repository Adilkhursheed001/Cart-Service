package com.example.CartService.client;

import com.example.CartService.model.Cart;
import com.example.CartService.model.Product;
import com.example.CartService.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

public class RestClient {

    @Autowired
    private static CartRepository cartRepository;

    public static Product getProduct(Long product_id , String token){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/product/" + product_id;

        // Sending a GET request and getting response as Product
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Send the request with headers
        ResponseEntity<Product> response = restTemplate.exchange(url, HttpMethod.GET, entity, Product.class);

        return response.getBody();
    }

        public static void sendCart(Cart cart) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
            String orderServiceUrl = "http://localhost:9000/orders/"; // Replace with actual URL of the Order service

            // Send request to Order service
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(orderServiceUrl, cart , String.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    emptyCart(cart);
                } else {
                    throw new Exception("Failed to finalize purchase");
                }
            } catch (Exception e) {
                throw new Exception("Error while sending order data: " + e.getMessage());
            }
        }

    private static void emptyCart(Cart cart) {
        cart.getCartItemList().clear();
        cartRepository.save(cart);
    }
}





