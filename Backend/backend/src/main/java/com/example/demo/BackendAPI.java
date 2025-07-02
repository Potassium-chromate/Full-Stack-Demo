package com.example.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.service.UserService;
import com.example.demo.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@RestController
public class BackendAPI {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/")
    public String hello() {
        return "Hey, Spring Boot Hello World!";
    }
    
    

    // Custom login API
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserEntity userEntity) {
        try {
            // Find user by username
            UserEntity user = userService.findUserByUsername(userEntity.getUsername());
            
            // Handle case where user is not found (return 401 Unauthorized)
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Can't find the user " + userEntity.getUsername());
            } 
            
            // Handle incorrect password case (return 401 Unauthorized)
            if (!passwordEncoder.matches(userEntity.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The password is incorrect!");
            }

            // If authentication is successful, create authentication token and set security context
            UsernamePasswordAuthenticationToken authenticationToken = 
                new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // Return 200 OK if login is successful
            return ResponseEntity.status(HttpStatus.OK).body("Login successful");

        } catch (Exception e) {
            // If authentication fails (e.g. server-side issues)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserEntity userEntity) {
    	UserEntity user = userService.findUserByUsername(userEntity.getUsername());
    	
    	try {
	    	// The user account is already existed
	    	if (user != null) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("Account: " + userEntity.getUsername() + " already existed!");
	        }
	    	userService.saveUser(userEntity);
	    	return ResponseEntity.status(HttpStatus.CREATED).body("Sign up successfully!");
    	}catch (Exception e) {
            // If authentication fails (e.g. server-side issues)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
        }
    }

    @GetMapping("/test_SQL")
    public String SQL_Test() {
        String query = "SELECT * FROM Course";

        
        // Query the database and get results as a list of maps
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        StringBuilder result = new StringBuilder();
        if (rows.isEmpty()) {
            result.append("No data found.");
        } else {
            for (Map<String, Object> row : rows) {
                String courseID = (String) row.get("c_id");
                String courseName = (String) row.get("c_name");
                String studentID = (String) row.get("t_id");

                result.append("courseID: ").append(courseID)
                      .append(", courseName: ").append(courseName)
                      .append(", studentID: ").append(studentID)
                      .append("<br>");
            }
        }

        return result.toString();
        
    }
    
}