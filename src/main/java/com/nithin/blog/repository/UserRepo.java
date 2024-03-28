package com.nithin.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nithin.blog.model.User;
// import java.util.List;


public interface UserRepo extends JpaRepository<User, Integer>{
    
    Optional<User> findByEmail(String email);
}
