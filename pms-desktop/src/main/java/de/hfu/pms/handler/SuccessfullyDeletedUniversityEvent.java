package de.hfu.pms.handler;

public class SuccessfullyDeletedUniversityEvent {

    private Long id;

    public SuccessfullyDeletedUniversityEvent(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
