package de.hfu.pms.events;

import de.hfu.pms.shared.dto.FacultyDTO;

public class RequestUpdateFacultyEvent {

    private FacultyDTO facultyDTO;

    public RequestUpdateFacultyEvent(FacultyDTO facultyDTO) {
        this.facultyDTO = facultyDTO;
    }

    public FacultyDTO getFaculty() {
        return facultyDTO;
    }
}
