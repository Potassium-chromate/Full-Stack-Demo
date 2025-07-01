package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer{

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for API if not using session-based authentication
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .antMatchers("/login", "/signup").permitAll() // Allow access to your custom login API
                .anyRequest().authenticated() // All other requests require authentication
            )
            .formLogin(formLogin -> formLogin.disable()); // Disable default form login

        return http.build();
    }
    
    @Bean
    protected static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Bean
    protected UserDetailsService userDetailsService() {
		UserDetails Eric = User.builder()
				.username("Eric")
				.password(passwordEncoder().encode("123321"))
				.roles("USER")
				.build();
		
		return new InMemoryUserDetailsManager(Eric);
	}

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS from your React frontend
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")  // Adjust to your React app URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);  // Allow cookies if needed
    }
}
