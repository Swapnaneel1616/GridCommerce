package com.ecommerce.product.service;

import com.ecommerce.product.dtos.ProductRequest;
import com.ecommerce.product.dtos.ProductResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);

    Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest);

    List<ProductResponse> getAllProducts();

    boolean deleteProduct(Long id);

    List<ProductResponse> searchProducts(String keyword);
}
