package com.app.ecom.repository;

import com.app.ecom.dto.ProductResponse;
import com.app.ecom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long> {
    List<Product> findByActiveTrue();

    @Query("select p from products p where p.active = true and p.stockQuantity>0 and lower(p.name) LIKE LOWER(concat('%', :keyword, '%'))")
    List<Product> searchProducts(@Param("keyword") String keyword);
}
