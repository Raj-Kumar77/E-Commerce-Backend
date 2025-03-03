package com.ecommercejava.Ecommerce_Shopping_Backend.repository;

import com.ecommercejava.Ecommerce_Shopping_Backend.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE product SET quantity = :quantity WHERE id = :pid", nativeQuery = true)
    public void updateProductQuantity(int quantity, UUID pid);

    @Transactional
    @Modifying
    @Query(value = "UPDATE product SET quantity_sold = :quantity WHERE id = :pid", nativeQuery = true)
    public void updateTotalProductQuantity(int quantity, UUID pid);
}
