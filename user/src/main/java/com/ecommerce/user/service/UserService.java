package com.ecommerce.user.service;

import com.ecommerce.user.dtos.UserRequest;
import com.ecommerce.user.dtos.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<UserResponse> fetchAllUsers();
    String addNewUsers(UserRequest userRequest);
    Optional<UserResponse> fetchUserById(String id);

    boolean updateUser(String id, UserRequest updatedUserRequest);
}
