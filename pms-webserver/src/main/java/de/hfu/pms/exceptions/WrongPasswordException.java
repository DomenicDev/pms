package de.hfu.pms.exceptions;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException(){
        super("Wrong Password!");
    }
}
