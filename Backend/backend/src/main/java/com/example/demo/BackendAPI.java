package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.servlet.http.HttpSession;

import com.example.demo.service.UserService;
import com.example.demo.service.CourseService;
import com.example.demo.model.CourseEntity;
import com.example.demo.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Arrays;
@RestController
public class BackendAPI {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/")
    public String hello() {
        return "Hey, Spring Boot Hello World!";
    }
    
    // Custom login API
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserEntity userEntity, HttpSession session) {
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
            // Create authority (role) for the user based on their role
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
            // If authentication is successful, create authentication token and set security context
            UsernamePasswordAuthenticationToken authenticationToken = 
                new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword(),  Arrays.asList(authority));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            System.out.println("Name: " + SecurityContextHolder.getContext().getAuthentication().getName());
            System.out.println("Auth: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            String sessionId = session.getId(); // Get the session ID
            System.out.println("Session ID: " + sessionId); // Print the session ID to the console
            
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
    
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/test_SQL")
    public ResponseEntity<List<CourseEntity>> SQL_Test() {
    	List<CourseEntity> courses = courseService.getCourseTable();
    	return ResponseEntity.status(HttpStatus.OK).body(courses);
    }
    
}