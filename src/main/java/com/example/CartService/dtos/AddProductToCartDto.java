package com.example.CartService.dtos;

import com.example.CartService.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductToCartDto {
    private int quantity ;
    private Long product_id;
}
