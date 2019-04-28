package de.hfu.pms.exceptions;

public class DoctoralStudentNotFoundException extends RuntimeException {

    public DoctoralStudentNotFoundException(Long id) {
        super("Could not find doctoral student with id " + id);
    }

}
