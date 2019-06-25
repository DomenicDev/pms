package de.hfu.pms.exceptions;

public class FacultyNotFoundException extends RuntimeException {

    public FacultyNotFoundException(Long id) {
        super("Could not find faculty with id " + id);
    }
}
