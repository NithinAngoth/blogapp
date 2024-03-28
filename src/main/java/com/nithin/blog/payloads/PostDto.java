package com.nithin.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostDto {

    private Integer postid;
    private String title;
    private String content;
    private String imagename;
    private Date addeddate;
    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto> comments = new HashSet<>();
}
