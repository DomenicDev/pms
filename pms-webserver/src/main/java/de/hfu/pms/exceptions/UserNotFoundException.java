package de.hfu.pms.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username){
        super("User with username " + username + " dosn't exists in the database.");
    }
}
