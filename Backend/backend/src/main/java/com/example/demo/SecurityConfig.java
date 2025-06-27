package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/").permitAll()  // Allow access to home page
            .antMatchers("/test_SQL").hasRole("USER")  // Secure /test_SQL
            .and()
            .httpBasic(); // Use HTTP Basic authentication for simplicity

        return http.build();
    }
    
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Service
    public static class CustomUserDetailsService implements UserDetailsService {
    	@Autowired
        private PasswordEncoder passwordEncoder;
    	
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            // Return hardcoded user for demo purposes
            if ("user".equals(username)) {
                return User.builder()
                        .username("user")
                        .password(passwordEncoder.encode("0000"))
                        .roles("USER")  // Assign USER role
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found: " + username);
            }
        }
    }
}
