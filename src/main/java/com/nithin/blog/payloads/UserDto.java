package com.nithin.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto {
    
    private int id;

    @NotEmpty
    @Size(min = 4, message = "Username must be at least 4 characters long!!")
    private String name;

    @Email(message =  "Invalid email format!!!")
    private String email;

    @NotEmpty
    // @Size(min = 3, max = 10, message = "password must be min of 3 character and less then 10 characters!!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit.")
    private String password;

    @NotEmpty
    private String about;

    private Set<RoleDto> roles = new HashSet<>();
}
