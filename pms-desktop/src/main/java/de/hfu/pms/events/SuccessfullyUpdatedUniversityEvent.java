package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UniversityDTO;

public class SuccessfullyUpdatedUniversityEvent {

    private UniversityDTO universityDTO;

    public SuccessfullyUpdatedUniversityEvent(UniversityDTO universityDTO) {
        this.universityDTO = universityDTO;
    }

    public UniversityDTO getUniversity() {
        return universityDTO;
    }
}
