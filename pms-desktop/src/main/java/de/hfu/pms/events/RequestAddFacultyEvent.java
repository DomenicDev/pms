package de.hfu.pms.events;

import de.hfu.pms.shared.dto.FacultyDTO;

public class RequestAddFacultyEvent {

    private final FacultyDTO faculty;

    public RequestAddFacultyEvent(FacultyDTO faculty) {
        this.faculty = faculty;
    }

    public FacultyDTO getFaculty() {
        return faculty;
    }
}
