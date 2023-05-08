package com.satriawinarah.dsp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "Phone Number is mandatory")
    @Pattern(regexp = "^\\d{10,13}$", message = "Phone number must be between 10 and 13 digits")
    private String phoneNumber;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 60, message = "Name must be at most 60 characters")
    private String name;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, max = 16, message = "Password must be between 6 and 16 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9]).+$", message = "Password must contain at least 1 capital letter and 1 number")
    private String password;

}