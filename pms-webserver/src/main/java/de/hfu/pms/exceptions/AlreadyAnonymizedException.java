package de.hfu.pms.exceptions;

public class AlreadyAnonymizedException extends RuntimeException {
    public AlreadyAnonymizedException(Long id){
        super("Student with id " + id + " is already anonymized.");
    }
}
