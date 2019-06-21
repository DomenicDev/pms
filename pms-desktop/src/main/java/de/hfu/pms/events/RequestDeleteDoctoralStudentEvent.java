package de.hfu.pms.events;

public class RequestDeleteDoctoralStudentEvent {

    private Long id;

    public RequestDeleteDoctoralStudentEvent(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
