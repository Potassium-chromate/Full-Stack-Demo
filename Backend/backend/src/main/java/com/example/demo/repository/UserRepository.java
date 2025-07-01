package com.example.demo.repository;
import com.example.demo.model.UserEntity;

public interface UserRepository{
    UserEntity findByUsername(String username);
}
