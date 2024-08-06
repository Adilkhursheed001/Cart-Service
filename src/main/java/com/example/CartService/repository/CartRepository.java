package com.example.CartService.repository;

import com.example.CartService.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {


     Cart save(Cart cart);


     Optional<Cart> findById(Long cart);
}
