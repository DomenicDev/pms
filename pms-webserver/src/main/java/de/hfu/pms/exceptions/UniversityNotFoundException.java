package de.hfu.pms.exceptions;

public class UniversityNotFoundException extends RuntimeException {

    public UniversityNotFoundException(Long id) {
        super("Could not find university with id " + id);
    }
}
