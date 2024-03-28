package com.nithin.blog.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nithin.blog.config.AppConstant;
import com.nithin.blog.exception.ResourceNotFoundException;
import com.nithin.blog.model.Role;
import com.nithin.blog.model.User;
import com.nithin.blog.payloads.UserDto;
import com.nithin.blog.repository.RoleRepo;
import com.nithin.blog.repository.UserRepo;
import com.nithin.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = this.userRepo.save(user);
        UserDto userDto1 = this.userToDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        this.userRepo.delete(user);
    }

    public User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class); // using modelmapper to map the fields from DTO to user
        // user.setId(userDto.getId());
        // user.setName(userDto.getName());
        // user.setEmail(userDto.getEmail());
        // user.setPassword(userDto.getPassword());
        // user.setAbout(userDto.getAbout());
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class); // using modelmapper to map the fields from source
                                                                     // object to destination object like user to
                                                                     // userDto
        // userDto.setId(user.getId());
        // userDto.setName(user.getName());
        // userDto.setEmail(user.getEmail());
        // userDto.setPassword(user.getPassword());
        // userDto.setAbout(user.getAbout());
        return userDto;
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);

        // encoded password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // roles
        Role role = this.roleRepo.findById(AppConstant.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser = this.userRepo.save(user);
        return this.modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto registerNewAdmin(UserDto UserDto) {

        User user =  this.modelMapper.map(UserDto, User.class);

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        Role roleAdmin = this.roleRepo.findById(AppConstant.ADMIN_USER).get();
        user.getRoles().add(roleAdmin);
        User newUserAdmin = this.userRepo.save(user);
        return this.modelMapper.map(newUserAdmin, UserDto.class);
    }

    

}
