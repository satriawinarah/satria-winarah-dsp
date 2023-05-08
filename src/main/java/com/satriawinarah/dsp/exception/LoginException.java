package com.satriawinarah.dsp.exception;

public class LoginException extends Exception {

    public LoginException() {
        super("Phone number or password is wrong");
    }

}
