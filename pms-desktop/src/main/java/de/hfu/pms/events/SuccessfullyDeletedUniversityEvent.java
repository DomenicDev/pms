package de.hfu.pms.events;

public class SuccessfullyDeletedUniversityEvent {

    private Long id;

    public SuccessfullyDeletedUniversityEvent(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
