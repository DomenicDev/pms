package de.hfu.pms.shared.dto;

import de.hfu.pms.shared.enums.Campus;
import de.hfu.pms.shared.enums.EmploymentLocation;

public class EmploymentDTO {

    private EmploymentLocation employmentLocation;
    private String kindOfEmployment;
    private Campus campusOfEmployment;
    private boolean preEmploymentTimeToBeCharged;

    public EmploymentDTO() {
    }

    public EmploymentDTO(EmploymentLocation employmentLocation, String kindOfEmployment, Campus campusOfEmployment, boolean preEmploymentTimeToBeCharged) {
        this.employmentLocation = employmentLocation;
        this.kindOfEmployment = kindOfEmployment;
        this.campusOfEmployment = campusOfEmployment;
        this.preEmploymentTimeToBeCharged = preEmploymentTimeToBeCharged;
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

    public Campus getCampusOfEmployment() {
        return campusOfEmployment;
    }

    public void setCampusOfEmployment(Campus campusOfEmployment) {
        this.campusOfEmployment = campusOfEmployment;
    }

    public boolean isPreEmploymentTimeToBeCharged() {
        return preEmploymentTimeToBeCharged;
    }

    public void setPreEmploymentTimeToBeCharged(boolean preEmploymentTimeToBeCharged) {
        this.preEmploymentTimeToBeCharged = preEmploymentTimeToBeCharged;
    }
}
