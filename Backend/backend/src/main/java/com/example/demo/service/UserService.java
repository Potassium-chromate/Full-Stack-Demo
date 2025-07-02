package com.example.demo.service;

import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;
import com.example.demo.model.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class UserService {
	
	@Autowired
    private PasswordEncoder passwordEncoder;  // Inject PasswordEncoder
	
    private final JdbcTemplate jdbcTemplate;

    // Inject JdbcTemplate via constructor
    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserEntity findUserByUsername(String username) {
        String query = "SELECT id, username, password, role FROM users WHERE username = ?";
        UserEntity return_entity = null;
        
        // Query the database and get results as a list of maps
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, username);

        if (!rows.isEmpty()) {
            Map<String, Object> row = rows.get(0); // Get the first row
            return_entity = new UserEntity();
            
            // Populate the UserEntity object with the data from the database
            return_entity.setId((Long) row.get("id"));  // Cast to Long
            return_entity.setUsername((String) row.get("username")); // Cast to String
            return_entity.setPassword((String) row.get("password")); // Cast to String
            return_entity.setRole((String) row.get("role")); // Cast to String
        }
        return return_entity;
    }
    
    public boolean saveUser(UserEntity userEntity) {
    	//Query if the account is already existed
    	String query = "SELECT id, username, password, role FROM users WHERE username = ?";
    	List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, userEntity.getUsername());
    	if (!rows.isEmpty()) {
    		System.out.println("Account" + userEntity.getUsername() + "is already existed.");
    		return false;
    	}
    	
    	String hashedPassword = passwordEncoder.encode(userEntity.getPassword()); 
    	query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
    	jdbcTemplate.update(query, 
    						userEntity.getUsername(),
    						hashedPassword,
    						userEntity.getRole());
    	return true;
    }
}
