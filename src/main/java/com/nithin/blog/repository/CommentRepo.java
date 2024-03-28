package com.nithin.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nithin.blog.model.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{
    
}
