package com.nithin.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {
    
    private Integer categoryId;

    @NotEmpty
    @Size(min = 4, message = "atleast four characters are required")
    private String categorytitle;

    @NotEmpty
    @Size(min = 5, message = "atleast five characters are required")
    private String categorydesc;
}
