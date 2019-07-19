package de.hfu.pms.events;


public class RequestDeleteFacultyEvent {

    private final Long id;

    public RequestDeleteFacultyEvent(Long id) {
        this.id = id;
    }

    public Long getFacultyId() {
        return id;
    }
}
