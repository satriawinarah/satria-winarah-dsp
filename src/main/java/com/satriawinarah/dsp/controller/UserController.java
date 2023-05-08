package com.satriawinarah.dsp.controller;

import com.satriawinarah.dsp.dto.LoginDto;
import com.satriawinarah.dsp.dto.UserDto;
import com.satriawinarah.dsp.exception.InvalidTokenException;
import com.satriawinarah.dsp.exception.LoginException;
import com.satriawinarah.dsp.exception.UserAlreadyExistException;
import com.satriawinarah.dsp.exception.UserNotFoundException;
import com.satriawinarah.dsp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register User")
    @PostMapping("/api/v1/auth/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody UserDto user) throws UserAlreadyExistException {
        return ResponseEntity.ok(userService.register(user.getPhoneNumber(), user.getName(), user.getPassword()));
    }

    @Operation(summary = "User Login")
    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) throws LoginException {
        return ResponseEntity.ok(userService.login(loginDto.getPhoneNumber(), loginDto.getPassword()));
    }

    @Operation(summary = "Get User Name by Phone Number")
    @GetMapping("/api/v1/user")
    public ResponseEntity<String> getName(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader)
            throws UserNotFoundException, InvalidTokenException {
        String phoneNumber = userService.parsePhoneNumberFromToken(authHeader);
        return ResponseEntity.ok(userService.getName(phoneNumber));
    }

    @Operation(summary = "Update User Name by Phone Number")
    @PutMapping("/api/v1/user")
    public ResponseEntity<String> updateName(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                             @RequestParam("name") @NotBlank(message = "Name is mandatory")
                                             @Size(max = 60, message = "Name must be at most 60 characters")
                                                     String name) throws UserNotFoundException, InvalidTokenException{
        String phoneNumber = userService.parsePhoneNumberFromToken(authHeader);
        return ResponseEntity.ok(userService.updateName(phoneNumber, name));
    }

}
