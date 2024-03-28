package com.nithin.blog.serviceimpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nithin.blog.payloads.CommentDto;
import com.nithin.blog.repository.CommentRepo;
import com.nithin.blog.repository.PostRespo;
import com.nithin.blog.service.CommentService;
import com.nithin.blog.exception.ResourceNotFoundException;
import com.nithin.blog.model.*;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRespo postRespo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override public CommentDto createComment(CommentDto commentDto, Integer postid) {
        Post posts = this.postRespo.findById(postid).orElseThrow(() -> new ResourceNotFoundException("post", "postid", postid));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(posts);
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment com = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "comment id", commentId));
        this.commentRepo.delete(com);
    }

}
