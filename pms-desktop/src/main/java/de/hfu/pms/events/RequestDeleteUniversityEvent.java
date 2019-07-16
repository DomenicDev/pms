package de.hfu.pms.events;

public class RequestDeleteUniversityEvent {

    private Long id;

    public RequestDeleteUniversityEvent(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
