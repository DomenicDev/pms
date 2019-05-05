package de.hfu.pms.exceptions;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException() {
        this(null);
    }

    public LoginFailedException(Throwable t) {
        super("Login failed", t);
    }

}
