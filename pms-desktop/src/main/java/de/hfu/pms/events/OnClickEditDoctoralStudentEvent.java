package de.hfu.pms.events;

public class OnClickEditDoctoralStudentEvent {

    private Long id;

    public OnClickEditDoctoralStudentEvent(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
