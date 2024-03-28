package com.nithin.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nithin.blog.payloads.ApiResponse;
import com.nithin.blog.payloads.UserDto;
import com.nithin.blog.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // post method for create the user including validation
    @PostMapping("/")
    public ResponseEntity<UserDto> createuser(@Valid @RequestBody UserDto userDto) {
        UserDto creaUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(creaUserDto, HttpStatus.CREATED);
    }

    // Get method for get all users
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    // Get method for getting the single user

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer uId) {
        return ResponseEntity.ok(this.userService.getUserById(uId));
    }

    // put method for updating the user details by id including validation
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
        UserDto updatedUser = this.userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    // ADMIN
    // delete method for deleting user by id\
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
        this.userService.deleteUser(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully", true), HttpStatus.OK);
    }
}
