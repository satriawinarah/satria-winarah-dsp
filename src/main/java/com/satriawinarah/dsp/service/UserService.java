package com.satriawinarah.dsp.service;

import com.satriawinarah.dsp.exception.InvalidTokenException;
import com.satriawinarah.dsp.exception.LoginException;
import com.satriawinarah.dsp.exception.UserAlreadyExistException;
import com.satriawinarah.dsp.exception.UserNotFoundException;

public interface UserService {

    String register(String phoneNumber, String name, String password) throws UserAlreadyExistException;
    String login(String phoneNumber, String password) throws LoginException;
    String getName(String phoneNumber) throws UserNotFoundException;
    String updateName(String phoneNumber, String name) throws UserNotFoundException;
    String parsePhoneNumberFromToken(String token) throws InvalidTokenException;

}
