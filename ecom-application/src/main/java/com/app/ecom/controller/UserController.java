package com.app.ecom.controller;

import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.User;
import com.app.ecom.service.UserService;
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
    public ResponseEntity<String> createUser(@RequestBody User user){
        return new ResponseEntity<>(userService.addNewUsers(user) , HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserByID(@PathVariable Long id){
        return userService.fetchUserById(id).map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id , @RequestBody User updatedUser){
        boolean updated = userService.updateUser(id , updatedUser);
        if (updated)
            return ResponseEntity.ok("User added successfully");
        return ResponseEntity.notFound().build();
    }

}
