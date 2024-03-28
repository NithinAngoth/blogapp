package com.nithin.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;

import com.nithin.blog.model.Category;
import com.nithin.blog.model.Post;
import com.nithin.blog.model.User;

public interface PostRespo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);

    //If your are getting problem in searching the kerywords in version of hibernate 5.6.7.final then you can use below query to recitfy it and 
    //also change in the controller parameter to line this.postService.searchByTitle("%" + keywords + "%")
    // @Query("select p from Post p where p.title like :key")
    // List<Post> searchByTitle(@Param("key")String title);
}
