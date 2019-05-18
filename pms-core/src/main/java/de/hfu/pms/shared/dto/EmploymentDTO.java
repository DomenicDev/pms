package de.hfu.pms.shared.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class EmploymentDTO {

    private Set<EmploymentEntryDTO> employmentEntries = new HashSet<>();

    public EmploymentDTO(Collection<EmploymentEntryDTO> employmentEntries) {
        this.employmentEntries.addAll(employmentEntries);
    }

    public Set<EmploymentEntryDTO> getEmploymentEntries() {
        return employmentEntries;
    }

    public void setEmploymentEntries(Set<EmploymentEntryDTO> employmentEntries) {
        this.employmentEntries = employmentEntries;
    }
}
