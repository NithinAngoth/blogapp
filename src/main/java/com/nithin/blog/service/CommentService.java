package com.nithin.blog.service;

import com.nithin.blog.payloads.CommentDto;

public interface CommentService {
    
    CommentDto createComment(CommentDto commentDto, Integer postid);
    void deleteComment(Integer commentId);
}
