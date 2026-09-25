package com.ecommerce.user.controller;

import com.ecommerce.user.dtos.UserRequest;
import com.ecommerce.user.dtos.UserResponse;
import com.ecommerce.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<>(userService.fetchAllUsers() , HttpStatus.OK) ;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        return new ResponseEntity<>(userService.addNewUsers(userRequest) , HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getUserByID(@PathVariable String id){
        return userService.fetchUserById(id).map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id , @RequestBody UserRequest updatedUserRequest){
        boolean updated = userService.updateUser(id , updatedUserRequest);
        if (updated)
            return ResponseEntity.ok("User added successfully");
        return ResponseEntity.notFound().build();
    }

}
