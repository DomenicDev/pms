package de.hfu.pms.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Set;

@Embeddable
public class Employment {

    @OneToMany
    @JoinColumn
    private Set<EmploymentEntry> employmentEntries;


    // GETTER AND SETTER

    public Set<EmploymentEntry> getEmploymentEntries() {
        return employmentEntries;
    }

    public void setEmploymentEntries(Set<EmploymentEntry> employmentEntries) {
        this.employmentEntries = employmentEntries;
    }
}
