package com.ecommerce.order.service;

import com.ecommerce.order.dtos.CartItemRequest;
import com.ecommerce.order.models.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    Boolean addToCart(String userId, CartItemRequest request);

    boolean deleteItemFromCart(String userId, String productId);

    List<CartItem> getCartItems(String userId);

    void clearCart(String userId);
}
