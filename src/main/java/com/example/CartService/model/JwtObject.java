package com.example.CartService.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class JwtObject {
 private Long id;

 private Long createdAt;

 private Long expiryAt;

 private List<String> roles;

 private Long userId;

}
