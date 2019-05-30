package de.hfu.pms.events;

import de.hfu.pms.shared.dto.FacultyDTO;

public class SuccessfullyDeletedFacultyEvent {

    private FacultyDTO facultyDTO;

    public SuccessfullyDeletedFacultyEvent(FacultyDTO facultyDTODTO) {
        this.facultyDTO = facultyDTO;
    }

    public FacultyDTO getFaculty() {
        return facultyDTO;
    }
}
