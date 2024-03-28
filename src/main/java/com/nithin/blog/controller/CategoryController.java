package com.nithin.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nithin.blog.payloads.ApiResponse;
import com.nithin.blog.payloads.CategoryDto;
import com.nithin.blog.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{categoryid}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
            @PathVariable("categoryid") Integer cid) {
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, cid);
        return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.ACCEPTED);
    }
    // delete
    @DeleteMapping("/{categoryid}")
    public ResponseEntity<ApiResponse> deleteCateogry(@PathVariable("categoryid") Integer cid){
        this.categoryService.deleteCategory(cid);
        return new ResponseEntity<>(new ApiResponse("Category is deleted successfully!!", true), HttpStatus.OK);
    }
    // get
    @GetMapping("/{categoryid}")
    public ResponseEntity<CategoryDto> getAllById(@PathVariable ("categoryid") Integer cid) {
        return ResponseEntity.ok(this.categoryService.getCategoryById(cid));
    }
    // get all
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        return ResponseEntity.ok(this.categoryService.getAllCategory());
    }
}
