package com.satriawinarah.dsp;

import com.satriawinarah.dsp.entity.UserEntity;
import com.satriawinarah.dsp.exception.InvalidTokenException;
import com.satriawinarah.dsp.exception.LoginException;
import com.satriawinarah.dsp.exception.UserAlreadyExistException;
import com.satriawinarah.dsp.exception.UserNotFoundException;
import com.satriawinarah.dsp.repository.UserRepository;
import com.satriawinarah.dsp.security.JwtTokenProvider;
import com.satriawinarah.dsp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity(null, "1234567890", "Test User", "$2a$10$F1zMAWm92y2QmXddPc7aLOzKe0leim1CfbMprMymfAfhgBjy.0Dbu");
    }

    @Test
    public void testRegister() throws UserAlreadyExistException {
        String phoneNumber = "1234567890";
        String name = "Satria Winarah";
        String password = "password";
        UserEntity userEntity = new UserEntity(null, phoneNumber, name, "encrypted_password");
        when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(password)).thenReturn("encrypted_password");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        
        String result = userService.register(phoneNumber, name, password);
        
        assertEquals("Success", result);
        verify(userRepository, times(1)).findByPhoneNumber(phoneNumber);
        verify(bCryptPasswordEncoder, times(1)).encode(password);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testLogin() throws LoginException {
        String phoneNumber = "1234567890";
        String password = "password";
        String token = "token";
        UserEntity userEntity = new UserEntity(null, phoneNumber, "Satria Winarah", "encrypted_password");
        when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(userEntity));
        when(bCryptPasswordEncoder.matches(password, userEntity.getPassword())).thenReturn(true);
        when(jwtTokenProvider.createToken(phoneNumber)).thenReturn(token);
        
        String result = userService.login(phoneNumber, password);
        
        assertEquals(token, result);
        verify(userRepository, times(1)).findByPhoneNumber(phoneNumber);
        verify(bCryptPasswordEncoder, times(1)).matches(password, userEntity.getPassword());
        verify(jwtTokenProvider, times(1)).createToken(phoneNumber);
    }

    @Test
    public void testGetName() throws UserNotFoundException {
        String phoneNumber = "1234567890";
        String name = "Satria Winarah";
        UserEntity userEntity = new UserEntity(null, phoneNumber, name, "encrypted_password");
        when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(userEntity));
        
        String result = userService.getName(phoneNumber);
        
        assertEquals(name, result);
        verify(userRepository, times(1)).findByPhoneNumber(phoneNumber);
    }

    @Test
    public void testUpdateName() throws UserNotFoundException {
        String phoneNumber = "1234567890";
        String name = "Satria Winarah";
        UserEntity userEntity = new UserEntity(null, phoneNumber, name, "encrypted_password");
        when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        String result = userService.updateName(phoneNumber, "Winarah Satria");

        assertEquals("Success", result);
        assertEquals("Winarah Satria", userEntity.getName());
        verify(userRepository, times(1)).findByPhoneNumber(phoneNumber);
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    public void testParsePhoneNumberFromToken() throws InvalidTokenException {
        String phoneNumber = "1234567890";
        String token = "token";
        when(jwtTokenProvider.validateToken(token)).thenReturn(phoneNumber);

        String result = userService.parsePhoneNumberFromToken(token);

        assertEquals(phoneNumber, result);
        verify(jwtTokenProvider, times(1)).validateToken(token);
    }
}
