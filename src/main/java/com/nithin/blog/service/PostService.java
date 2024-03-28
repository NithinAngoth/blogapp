package com.nithin.blog.service;

import java.util.List;

// import com.nithin.blog.model.Post;
import com.nithin.blog.payloads.PostDto;
import com.nithin.blog.payloads.PostResponse;

public interface PostService {

    // create
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    // update

    PostDto updatePost(PostDto postDto, Integer postid);

    // delete
    void deletePost(Integer postid);

    // get
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    // get single post
    PostDto getPostById(Integer postid);

    // get all psots by category

    List<PostDto> getPostByCategory(Integer categoryid);

    // get all posts by user
    List<PostDto> getPostByUser(Integer userid);

    // search posts
    List<PostDto> searchPosts(String keyword);


}
