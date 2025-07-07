package com.example.demo.repository;

import com.example.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Custom method with JPQL query
    @Query("SELECT c FROM UserEntity c WHERE c.username = :userName")
    List<UserEntity> findUserByUsername(String userName);
}
