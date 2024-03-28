package com.nithin.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nithin.blog.exception.ApiException;
import com.nithin.blog.payloads.JwtAuthRequest;
import com.nithin.blog.payloads.JwtAuthResponse;
import com.nithin.blog.payloads.UserDto;
import com.nithin.blog.security.JwtTokenHelper;
import com.nithin.blog.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest request) throws Exception {

        this.authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            this.authenticationManager.authenticate(authToken);
        } catch (BadCredentialsException e) {
            System.out.println("Invalid Details!!");
            throw new ApiException("Invalid username or password");
        }

    }

    // register a new user
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        UserDto registeredUser = this.userService.registerNewUser(userDto);
        return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
    }

    // register a new admin
    @PostMapping("/register/admin")
    public ResponseEntity<UserDto> registerUserAdmin(@RequestBody UserDto userDto){
    UserDto registeredAdmin = this.userService.registerNewAdmin(userDto);
    return new ResponseEntity<UserDto>(registeredAdmin, HttpStatus.CREATED);
    }
    // public ResponseEntity<UserDto> registerUserAdmin(@RequestBody UserDto userDto, Authentication authenticate) {
    //     if (!userDetails.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN_USER"))) {
    //         throw new AccessDeniedException("you don't have permission to perform this action");
    //     }
    //     UserDto registeredAdmin = this.userService.registerNewAdmin(userDto);
    //     return new ResponseEntity<>(registeredAdmin, HttpStatus.CREATED);
    // }
}
