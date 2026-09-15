package com.app.ecom.service;

import com.app.ecom.dto.CartItemRequest;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    Boolean addToCart(String userId, CartItemRequest request);
}
