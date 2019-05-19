package de.hfu.pms.events;

import de.hfu.pms.shared.dto.UniversityDTO;

public class RequestAddUniversityEvent {

    private final UniversityDTO university;

    public RequestAddUniversityEvent(UniversityDTO university) {
        this.university = university;
    }

    public UniversityDTO getUniversity() {
        return university;
    }
}
