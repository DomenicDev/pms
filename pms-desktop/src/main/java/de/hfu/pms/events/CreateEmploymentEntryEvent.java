package de.hfu.pms.events;

import de.hfu.pms.shared.dto.EmploymentEntryDTO;

/**
 * This event class is used to inform about a new employment entry
 * that has been created in the gui.
 */
public class CreateEmploymentEntryEvent {

    private final EmploymentEntryDTO employmentEntryDTO;

    /**
     * Creates the event with the specified employment entry
     *
     * @param employmentEntryDTO the entry that has been created
     */
    public CreateEmploymentEntryEvent(EmploymentEntryDTO employmentEntryDTO) {
        this.employmentEntryDTO = employmentEntryDTO;
    }

    public EmploymentEntryDTO getEmploymentEntryDTO() {
        return employmentEntryDTO;
    }
}
