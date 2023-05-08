package com.satriawinarah.dsp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class LoginDto {

    @NotBlank(message = "Phone Number is mandatory")
    @Pattern(regexp = "^\\d{10,13}$", message = "Phone number must be between 10 and 13 digits")
    private String phoneNumber;

    @NotBlank(message = "Password is mandatory")
    private String password;

}
