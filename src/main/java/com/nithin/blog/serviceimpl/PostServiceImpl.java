package com.nithin.blog.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.nithin.blog.payloads.PostDto;
import com.nithin.blog.payloads.PostResponse;
import com.nithin.blog.repository.CategoryRepo;
import com.nithin.blog.repository.PostRespo;
import com.nithin.blog.repository.UserRepo;
import com.nithin.blog.service.PostService;
import com.nithin.blog.exception.ResourceNotFoundException;
import com.nithin.blog.model.*;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRespo postRespo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));
        Post post = this.dtoToPost(postDto);
        post.setImagename("default.png");
        post.setAddeddate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post savedPost = this.postRespo.save(post);
        return this.postToDto(savedPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postid) {
        Post post = this.postRespo.findById(postid)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", postid));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImagename(postDto.getImagename());

        Post updatedPost = this.postRespo.save(post);
        PostDto postDtos = this.postToDto(updatedPost);
        return postDtos;
    }

    @Override
    public void deletePost(Integer postid) {
        Post post = this.postRespo.findById(postid)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", postid));
        this.postRespo.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = null;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = this.postRespo.findAll(pageable);
        List<Post> posts = pagePost.getContent();
        List<PostDto> postDtos = posts.stream().map(post -> this.postToDto(post)).collect(Collectors.toList());
        PostResponse response = new PostResponse();
        response.setContent(postDtos);
        response.setPageNumber(pagePost.getNumber());
        response.setPageSize(pagePost.getSize());
        response.setTotalElements(pagePost.getTotalElements());
        response.setTotalPages(pagePost.getTotalPages());
        response.setLastPage(pagePost.isLast());
        return response;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRespo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        return this.postToDto(post);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryid) {
        Category cat = this.categoryRepo.findById(categoryid)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryid));
        List<Post> post = this.postRespo.findByCategory(cat);

        List<PostDto> postDtos = post.stream().map(posts -> this.postToDto(posts)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userid) {
        User user = this.userRepo.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userid));
        List<Post> post = this.postRespo.findByUser(user);

        List<PostDto> postDtos = post.stream().map(posts -> this.postToDto(posts)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRespo.findByTitleContaining(keyword);
        List<PostDto> postDtos = posts.stream().map((post) -> this.postToDto(post)).collect(Collectors.toList());
        return postDtos;
    }


    public Post dtoToPost(PostDto postDto) {
        Post post = this.modelMapper.map(postDto, Post.class);
        return post;
    }

    public PostDto postToDto(Post post) {
        PostDto postDto = this.modelMapper.map(post, PostDto.class);
        return postDto;
    }

}
