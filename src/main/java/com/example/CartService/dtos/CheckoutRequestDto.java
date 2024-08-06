package com.example.CartService.dtos;

import com.example.CartService.model.Cart;
import com.example.CartService.model.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutRequestDto {
    private Long cart_id;
}
