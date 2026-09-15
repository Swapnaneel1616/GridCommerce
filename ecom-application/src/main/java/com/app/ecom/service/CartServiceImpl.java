package com.app.ecom.service;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements  CartService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    //Intuition behind this-> Check if the product quantity exists and user exists
    //If yes then we will check whether user is adding the same item in the cart or adding a new item
    public Boolean addToCart(String userId, CartItemRequest request) {
        //Look for product
        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        if(productOpt.isEmpty())
            return false;
        Product product = productOpt.get();
        if(product.getStockQuantity() < request.getQuantity()){
            return false;
        }

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        if(userOpt.isEmpty())
            return false;

        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if(existingCartItem!= null){
            //Update the quantity since the cart Item already exists for the User .
            existingCartItem.setQuantity(existingCartItem.getQuantity()+ request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(existingCartItem.getPrice()));
            cartItemRepository.save(existingCartItem);
        }
        else{
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    @Override
    public boolean deleteItemFromCart(String userId, Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if(productOpt.isEmpty())
            return false;

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        if(userOpt.isEmpty())
            return false;

        CartItem cartItem = cartItemRepository.findByUserAndProduct(userOpt.get(), productOpt.get());
        if (cartItem == null){
            return false;
        }
        cartItemRepository.delete(cartItem);
        return true;
    }


    
    @Override
    public List<CartItem> getCartItems(String userId) {
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow();
        return cartItemRepository.findByUser(user);
    }
}
