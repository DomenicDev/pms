package de.hfu.pms.exceptions;

public class SubmitException extends RuntimeException {

    private String message;

    public SubmitException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
