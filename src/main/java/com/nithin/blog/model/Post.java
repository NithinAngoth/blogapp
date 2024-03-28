package com.nithin.blog.model;

import java.util.ArrayList;
import java.util.Date;
// import java.util.HashSet;
import java.util.List;
// import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@Data
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postid;
    private String title;
    private String content;
    private String imagename;
    private Date addeddate;

    @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    // private Set<Comment> comments = new HashSet<>();
    private List<Comment> comments = new ArrayList<>();
}
