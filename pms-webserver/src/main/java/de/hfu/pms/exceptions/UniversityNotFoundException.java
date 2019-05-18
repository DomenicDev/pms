package de.hfu.pms.exceptions;

public class UniversityNotFoundException extends RuntimeException {

    public UniversityNotFoundException(Integer id) {
        super("Could not find university with id " + id);
    }
}
