package com.nithin.blog.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nithin.blog.exception.ResourceNotFoundException;
import com.nithin.blog.model.Category;
import com.nithin.blog.payloads.CategoryDto;
import com.nithin.blog.repository.CategoryRepo;
import com.nithin.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.dtoToCategory(categoryDto);
        Category savedCategory = this.categoryRepo.save(category);
        return this.categoryToDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryid) {
        Category exisitingCategory = this.categoryRepo.findById(categoryid)
                .orElseThrow(() -> new ResourceNotFoundException("exisitingCategory", "id", categoryid));
        exisitingCategory.setCategorytitle(categoryDto.getCategorytitle());
        exisitingCategory.setCategorydesc(categoryDto.getCategorydesc());

        Category updatedCategory = this.categoryRepo.save(exisitingCategory);
        CategoryDto categoryDto1 = this.categoryToDto(updatedCategory);
        return categoryDto1;
    }

    @Override
    public void deleteCategory(Integer categoryid) {
        Category category = this.categoryRepo.findById(categoryid)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryid));
        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryid) {
        Category category = this.categoryRepo.findById(categoryid)
            .orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryid));
        return this.categoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map(category -> this.categoryToDto(category)).collect(Collectors.toList());
        return categoryDtos;
    }

    public Category dtoToCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        return category;
    }

    public CategoryDto categoryToDto(Category category) {
        CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }
}
