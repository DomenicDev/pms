package de.hfu.pms.exceptions;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException() {
        super("Login failed.");
    }

    public LoginFailedException(Throwable t) {
        super(t);
    }

}
