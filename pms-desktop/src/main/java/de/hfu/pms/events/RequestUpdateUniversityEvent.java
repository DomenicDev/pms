package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UniversityDTO;

public class RequestUpdateUniversityEvent {

    private UniversityDTO universityDTO;

    public RequestUpdateUniversityEvent(UniversityDTO universityDTO) {
        this.universityDTO = universityDTO;
    }

    public UniversityDTO getUniversity() {
        return universityDTO;
    }
}
