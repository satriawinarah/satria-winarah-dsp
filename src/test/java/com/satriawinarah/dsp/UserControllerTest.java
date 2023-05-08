package com.satriawinarah.dsp;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.satriawinarah.dsp.controller.UserController;
import com.satriawinarah.dsp.dto.LoginDto;
import com.satriawinarah.dsp.dto.UserDto;
import com.satriawinarah.dsp.exception.InvalidTokenException;
import com.satriawinarah.dsp.exception.LoginException;
import com.satriawinarah.dsp.exception.UserAlreadyExistException;
import com.satriawinarah.dsp.exception.UserNotFoundException;
import com.satriawinarah.dsp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistration() throws UserAlreadyExistException {
        UserDto userDto = new UserDto();
        userDto.setName("Alice");
        userDto.setPhoneNumber("1234567890");
        userDto.setPassword("password");

        when(userService.register(anyString(), anyString(), anyString())).thenReturn("success");

        ResponseEntity<String> responseEntity = userController.registration(userDto);

        verify(userService).register(eq("1234567890"), eq("Alice"), eq("password"));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody());
    }

    @Test
    void testLogin() throws LoginException {
        LoginDto loginDto = new LoginDto();
        loginDto.setPhoneNumber("1234567890");
        loginDto.setPassword("password");

        when(userService.login(anyString(), anyString())).thenReturn("token");

        ResponseEntity<String> responseEntity = userController.login(loginDto);

        verify(userService).login(eq("1234567890"), eq("password"));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("token", responseEntity.getBody());
    }

    @Test
    void testGetName() throws UserNotFoundException, InvalidTokenException {
        String token = "Bearer abcdefg";
        String phoneNumber = "1234567890";
        String name = "Alice";

        when(userService.parsePhoneNumberFromToken(eq(token))).thenReturn(phoneNumber);
        when(userService.getName(eq(phoneNumber))).thenReturn(name);

        ResponseEntity<String> responseEntity = userController.getName(token);

        verify(userService).parsePhoneNumberFromToken(eq(token));
        verify(userService).getName(eq(phoneNumber));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(name, responseEntity.getBody());
    }

    @Test
    void testUpdateName() throws UserNotFoundException, InvalidTokenException {
        String token = "Bearer abcdefg";
        String phoneNumber = "1234567890";
        String name = "Alice";

        when(userService.parsePhoneNumberFromToken(eq(token))).thenReturn(phoneNumber);
        when(userService.updateName(eq(phoneNumber), eq(name))).thenReturn("success");

        ResponseEntity<String> responseEntity = userController.updateName(token, name);

        verify(userService).parsePhoneNumberFromToken(eq(token));
        verify(userService).updateName(eq(phoneNumber), eq(name));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody());
    }

}
