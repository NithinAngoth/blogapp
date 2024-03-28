package com.nithin.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nithin.blog.model.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{
    
}
