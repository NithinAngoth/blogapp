package com.nithin.blog.service;

import java.util.List;

import com.nithin.blog.payloads.CategoryDto;

public interface CategoryService {

    // create
    CategoryDto createCategory(CategoryDto categoryDto);
    // update
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryid);
    // delete
    void deleteCategory(Integer  categoryid);
    // get
    CategoryDto getCategoryById(Integer  categoryid);
    // getall
    List<CategoryDto> getAllCategory();
}
