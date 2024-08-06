package com.example.CartService.repository;

import com.example.CartService.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findById(Long ProductId);


    Product save(Product product);

    List<Product> findAll();
}
