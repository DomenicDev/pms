package de.hfu.pms.model;

import de.hfu.pms.shared.enums.Campus;
import de.hfu.pms.shared.enums.EmploymentLocation;

import javax.persistence.*;

@Entity
@Table
public class EmploymentEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated
    private EmploymentLocation employmentLocation;

    @Column
    private String kindOfEmployment;

    @Column
    @Enumerated
    private Campus campusOfDeployment;

    @Column
    private boolean preEmploymentTimeToBeCharged;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public EmploymentLocation getEmploymentLocation() {
        return employmentLocation;
    }

    public void setEmploymentLocation(EmploymentLocation employmentLocation) {
        this.employmentLocation = employmentLocation;
    }

    public String getKindOfEmployment() {
        return kindOfEmployment;
    }

    public void setKindOfEmployment(String kindOfEmployment) {
        this.kindOfEmployment = kindOfEmployment;
    }

    public Campus getCampusOfDeployment() {
        return campusOfDeployment;
    }

    public void setCampusOfDeployment(Campus campusOfDeployment) {
        this.campusOfDeployment = campusOfDeployment;
    }

    public boolean isPreEmploymentTimeToBeCharged() {
        return preEmploymentTimeToBeCharged;
    }

    public void setPreEmploymentTimeToBeCharged(boolean preEmploymentTimeToBeCharged) {
        this.preEmploymentTimeToBeCharged = preEmploymentTimeToBeCharged;
    }
}
