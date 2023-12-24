package com.example.SpringBootProjekat.services;

import com.example.SpringBootProjekat.entities.UserEntity;
import com.example.SpringBootProjekat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {



    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public boolean registerUser(UserEntity user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return false;
        } else {
            System.out.println(user.getUsername());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("user");
            userRepository.save(user);
            return true;
        }
    }

    public boolean authenticateUser(String username, String password) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }

    public UserEntity findUserbyId(Long id){
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found with ID: " + id));
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAllUsers();
    }

    public void deleteUser(Long id){
        UserEntity deletionUser = userRepository.findById(id).orElseThrow();
        userRepository.delete(deletionUser);
    }


}
