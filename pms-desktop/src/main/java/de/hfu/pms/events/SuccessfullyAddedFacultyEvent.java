package de.hfu.pms.events;

import de.hfu.pms.shared.dto.FacultyDTO;

public class SuccessfullyAddedFacultyEvent {

    private FacultyDTO facultyDTO;

    public SuccessfullyAddedFacultyEvent(FacultyDTO facultyDTO) {
        this.facultyDTO = facultyDTO;
    }

    public FacultyDTO getFaculty() {
        return facultyDTO;
    }
}
