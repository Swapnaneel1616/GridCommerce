package com.app.ecom.service;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.model.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    Boolean addToCart(String userId, CartItemRequest request);

    boolean deleteItemFromCart(String userId, Long productId);

    List<CartItem> getCartItems(String userId);
}
