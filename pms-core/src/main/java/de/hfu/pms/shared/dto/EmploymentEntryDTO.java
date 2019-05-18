package de.hfu.pms.shared.dto;

import de.hfu.pms.shared.enums.Campus;
import de.hfu.pms.shared.enums.EmploymentLocation;

public class EmploymentEntryDTO {

    private Long id;
    private EmploymentLocation employmentLocation;
    private String kindOfEmployment;
    private Campus campusOfDeployment;
    private boolean preEmploymentTimeToBeCharged;

    public void EmploymentEntryDTO(Long id, EmploymentLocation employmentLocation, String kindOfEmployment, Campus campusOfDeployment, boolean preEmploymentTimeToBeCharged){
        this.id = id;
        this.employmentLocation = employmentLocation;
        this.kindOfEmployment = kindOfEmployment;
        this.campusOfDeployment = campusOfDeployment;
        this.preEmploymentTimeToBeCharged = preEmploymentTimeToBeCharged;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
