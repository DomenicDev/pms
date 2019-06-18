package de.hfu.pms.events;

public class SuccessfullyDeletedDoctoralStudentEvent {

    private Long id;

    public SuccessfullyDeletedDoctoralStudentEvent(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
