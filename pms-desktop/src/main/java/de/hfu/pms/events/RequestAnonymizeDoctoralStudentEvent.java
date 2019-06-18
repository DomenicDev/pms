package de.hfu.pms.events;

public class RequestAnonymizeDoctoralStudentEvent {
    private Long id;

    public RequestAnonymizeDoctoralStudentEvent(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
