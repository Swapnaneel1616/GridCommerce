package com.ecommerce.order.service;

import com.ecommerce.order.dtos.OrderItemDTO;
import com.ecommerce.order.dtos.OrderResponse;
import com.ecommerce.order.repository.OrderRepository;
import com.ecommerce.order.models.CartItem;
import com.ecommerce.order.models.Order;
import com.ecommerce.order.models.OrderItem;
import com.ecommerce.order.models.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Override
    public Optional<OrderResponse> createOrder(Long userId) {

        //Validate for cart items
        List<CartItem> cartItems = cartService.getCartItems(String.valueOf(userId));
        if(cartItems.isEmpty()){
            return Optional.empty();
        }
        //Validate for user
//        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
//        if(userOptional.isEmpty()){
//
//        }
//        User user = userOptional.get();
        //Calculate total price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO , BigDecimal::add);
        //Create order
        Order order = new Order();
        List<OrderItem> orderItems = cartItems.stream()
                        .map(item->new OrderItem(
                                null,
                                item.getProductId(),
                                item.getQuantity(),
                                item.getPrice(),
                                order
                        )).toList();
        order.setItems(orderItems);
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        Order savedOrder = orderRepository.save(order);

        //Clear the cart
        cartService.clearCart(String.valueOf(userId));
        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order savedOrder) {
        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus(),
                savedOrder.getItems().stream()
                        .map(item->new OrderItemDTO(
                                item.getId(),
                                item.getProductId(),
                                item.getQuantity(),
                                item.getPrice(),
                                item.getPrice().multiply(new BigDecimal(item.getQuantity()))
                        )).toList(),
                savedOrder.getCreatedAt()

        );
    }
}
