package de.hfu.pms.events;

public class RequestAnonymiziseDoctoralStudentEvent {
    private Long id;

    public RequestAnonymiziseDoctoralStudentEvent(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
