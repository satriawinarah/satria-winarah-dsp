package com.satriawinarah.dsp.service.impl;

import com.satriawinarah.dsp.entity.UserEntity;
import com.satriawinarah.dsp.exception.InvalidTokenException;
import com.satriawinarah.dsp.exception.UserAlreadyExistException;
import com.satriawinarah.dsp.exception.LoginException;
import com.satriawinarah.dsp.exception.UserNotFoundException;
import com.satriawinarah.dsp.repository.UserRepository;
import com.satriawinarah.dsp.security.JwtTokenProvider;
import com.satriawinarah.dsp.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String register(String phoneNumber, String name, String password) throws UserAlreadyExistException {
        Optional<UserEntity> user = userRepository.findByPhoneNumber(phoneNumber);
        if (user.isPresent()) {
            throw new UserAlreadyExistException();
        }

        userRepository.save(new UserEntity(null, phoneNumber, name, bCryptPasswordEncoder.encode(password)));
        return "Success";
    }

    @Override
    public String login(String phoneNumber, String password) throws LoginException {
        Optional<UserEntity> user = userRepository.findByPhoneNumber(phoneNumber);
        if (user.isPresent() && bCryptPasswordEncoder.matches(password, user.get().getPassword())) {
            return jwtTokenProvider.createToken(phoneNumber);
        }

        throw new LoginException();
    }

    @Override
    public String getName(String phoneNumber) throws UserNotFoundException {
        Optional<UserEntity> user = userRepository.findByPhoneNumber(phoneNumber);
        if (user.isPresent()) {
            return user.get().getName();
        }
        throw new UserNotFoundException();
    }

    @Override
    public String updateName(String phoneNumber, String name) throws UserNotFoundException {
        Optional<UserEntity> user = userRepository.findByPhoneNumber(phoneNumber);
        if (user.isPresent()) {
            user.get().setName(name);
            userRepository.save(user.get());
            return "Success";
        }
        throw new UserNotFoundException();
    }

    @Override
    public String parsePhoneNumberFromToken(String token) throws InvalidTokenException {
        return jwtTokenProvider.validateToken(token);
    }
}
