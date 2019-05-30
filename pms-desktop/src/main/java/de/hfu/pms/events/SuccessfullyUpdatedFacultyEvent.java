package de.hfu.pms.events;

import de.hfu.pms.shared.dto.FacultyDTO;

public class SuccessfullyUpdatedFacultyEvent {

    private FacultyDTO facultyDTO;

    public SuccessfullyUpdatedFacultyEvent(FacultyDTO universityDTO) {
        this.facultyDTO = universityDTO;
    }

    public FacultyDTO getUniversity() {
        return facultyDTO;
    }
}
