package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UniversityDTO;

public class SuccessfullyAddedUniversityEvent {

    private UniversityDTO universityDTO;

    public SuccessfullyAddedUniversityEvent(UniversityDTO universityDTO) {
        this.universityDTO = universityDTO;
    }

    public UniversityDTO getUniversity() {
        return universityDTO;
    }
}
