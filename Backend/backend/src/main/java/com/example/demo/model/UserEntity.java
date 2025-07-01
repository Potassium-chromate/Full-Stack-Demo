package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;
import java.util.HashMap;

@Entity
public class UserEntity {

    @Id
    private Long id;
    private String username;
    private String password;
    private String role;

    // Default constructor
    public UserEntity() {
    	id = null;
    	username = null;
    	password = null;
    	role = null;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Custom method to return user data in a Map (optional)
    public Map<String, Object> getUserEntity() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", this.id);
        userMap.put("username", this.username);
        userMap.put("password", this.password);
        userMap.put("role", this.role);
        return userMap;
    }
}
