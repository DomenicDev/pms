package de.hfu.pms.events;

import de.hfu.pms.shared.dto.FacultyDTO;

public class SuccessfullyAddedFacultyEvent {

    private FacultyDTO facultyDTO;

    public SuccessfullyAddedFacultyEvent(FacultyDTO universityDTO) {
        this.facultyDTO = universityDTO;
    }

    public FacultyDTO getUniversity() {
        return facultyDTO;
    }
}
