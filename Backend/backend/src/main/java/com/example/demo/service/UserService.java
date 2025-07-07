package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepository;

import java.util.List;
import java.util.Objects;
@Service
public class UserService {

	private final PasswordEncoder passwordEncoder;
    private final UserRepository  userRepository;
    public UserService(PasswordEncoder passwordEncoder,
		               UserRepository  userRepository) {
		this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
		this.userRepository  = Objects.requireNonNull(userRepository);
    }

    // Find a user by username
    public UserEntity findUserByUsername(String username) {
        // Call the repository method to find user by username
    	System.out.println(username);
        List<UserEntity> users = userRepository.findUserByUsername(username);
        
        // If a user is found, return the first match (assuming usernames are unique)
        if (!users.isEmpty()) {
            return users.get(0); // Return the first match
        }
        
        // If no user found, return null or handle it accordingly
        return null;
    }

    // Save a new user to the database
    public boolean saveUser(UserEntity userEntity) {
        // Check if the user already exists by username
        UserEntity existingUser = findUserByUsername(userEntity.getUsername());
        if (existingUser != null) {
            System.out.println("Account with username " + userEntity.getUsername() + " already exists.");
            return false;  // Return false if user exists
        }

        // Encode the password before saving the user
        String hashedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(hashedPassword);  // Set the encoded password

        // Save the new user to the database
        userRepository.save(userEntity);
        return true;  // Return true if the user is saved successfully
    }
}
