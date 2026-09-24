package com.ecommerce.order.service;

import com.ecommerce.order.dtos.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface OrderService {
    Optional<OrderResponse> createOrder(Long userId);
}
