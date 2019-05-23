package de.hfu.pms.shared.dto;

import de.hfu.pms.shared.enums.Campus;

import java.time.LocalDate;

public class EmploymentEntryDTO {

    private Long id;
    private String employmentLocation;
    private String kindOfEmployment;
    private Campus campusOfDeployment;
    private LocalDate employmentBegin;
    private LocalDate employmentEnd;

    public EmploymentEntryDTO() {
    }

    public EmploymentEntryDTO(Long id, String employmentLocation, String kindOfEmployment, Campus campusOfDeployment, LocalDate employmentBegin, LocalDate employmentEnd) {
        this.id = id;
        this.employmentLocation = employmentLocation;
        this.kindOfEmployment = kindOfEmployment;
        this.campusOfDeployment = campusOfDeployment;
        this.employmentBegin = employmentBegin;
        this.employmentEnd = employmentEnd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmploymentLocation() {
        return employmentLocation;
    }

    public void setEmploymentLocation(String employmentLocation) {
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

    public LocalDate getEmploymentBegin() {
        return employmentBegin;
    }

    public void setEmploymentBegin(LocalDate employmentBegin) {
        this.employmentBegin = employmentBegin;
    }

    public LocalDate getEmploymentEnd() {
        return employmentEnd;
    }

    public void setEmploymentEnd(LocalDate employmentEnd) {
        this.employmentEnd = employmentEnd;
    }

    @Override
    public String toString() {
        return "EmploymentEntryDTO{" +
                "id=" + id +
                ", employmentLocation='" + employmentLocation + '\'' +
                ", kindOfEmployment='" + kindOfEmployment + '\'' +
                ", campusOfDeployment=" + campusOfDeployment +
                ", employmentBegin=" + employmentBegin +
                ", employmentEnd=" + employmentEnd +
                '}';
    }
}
