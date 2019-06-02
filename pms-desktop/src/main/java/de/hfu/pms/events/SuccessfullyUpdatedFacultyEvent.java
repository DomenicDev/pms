package de.hfu.pms.events;

import de.hfu.pms.shared.dto.FacultyDTO;

public class SuccessfullyUpdatedFacultyEvent {

    private FacultyDTO facultyDTO;

    public SuccessfullyUpdatedFacultyEvent(FacultyDTO facultyDTOO) {
        this.facultyDTO = facultyDTOO;
    }

    public FacultyDTO getFaculty() {
        return facultyDTO;
    }
}
