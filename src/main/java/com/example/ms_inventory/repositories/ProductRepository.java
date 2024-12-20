package com.example.ms_inventory.repositories;

import com.example.ms_inventory.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderId(Long orderId);

    @Query("SELECT c FROM Product c WHERE c.status = :status")
    List<Product> findAllProductsByStatus(@Param("status") String status);
}
