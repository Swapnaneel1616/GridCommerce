package com.app.ecom.service;

import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.repository.UserRepository;
import com.app.ecom.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserResponse> fetchAllUsers(){
        return userRepository.findAll().stream().
                map(this::mapToUserResponse).
                collect(Collectors.toList()) ;
    }

    public String addNewUsers(User user){
        userRepository.save(user);
        return "User created successfully with id "+ user.getId();
    }
    @Override
    public Optional<User> fetchUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(existingUser ->{
                    existingUser.setFirstName(updatedUser.getFirstName());
                existingUser.setLastName(updatedUser.getLastName());
                userRepository.save(existingUser);
                return true;
                }).orElse(false);
    }

    private UserResponse mapToUserResponse(User user){

        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhone(user.getPhone());
        response.setEmail(user.getEmail());
        response.setUserRole(user.getRole());

        if(user.getAddress()!= null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddressDTO(addressDTO);
        }

        return response;

    }
}
