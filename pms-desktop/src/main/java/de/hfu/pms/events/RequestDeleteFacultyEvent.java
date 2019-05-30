package de.hfu.pms.events;

import de.hfu.pms.shared.dto.FacultyDTO;

public class RequestDeleteFacultyEvent {

    private final FacultyDTO faculty;

    public RequestDeleteFacultyEvent(FacultyDTO faculty) {
        this.faculty = faculty;
    }

    public FacultyDTO getFaculty() {
        return faculty;
    }
}
