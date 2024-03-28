package com.nithin.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nithin.blog.payloads.UserDto;

@Service
public interface UserService {

    UserDto registerNewUser(UserDto user);

    UserDto registerNewAdmin(UserDto userDto);

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Integer userId);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);
}
