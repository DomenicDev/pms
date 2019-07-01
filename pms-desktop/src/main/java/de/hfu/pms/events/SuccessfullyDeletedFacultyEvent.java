package de.hfu.pms.events;

public class SuccessfullyDeletedFacultyEvent {

    private Long facultyId;

    public SuccessfullyDeletedFacultyEvent(Long facultyId) {
        this.facultyId = facultyId;
    }

    public Long getFaculty() {
        return facultyId;
    }
}
