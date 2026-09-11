package com.app.ecom.service;

import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<UserResponse> fetchAllUsers();
    String addNewUsers(User user);
    Optional<User> fetchUserById(Long id);

    boolean updateUser(Long id, User updatedUser);
}
