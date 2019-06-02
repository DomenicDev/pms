package de.hfu.pms.model;

import de.hfu.pms.shared.enums.Campus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class EmploymentEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String employmentLocation;

    @Column
    private String kindOfEmployment;

    @Column
    @Enumerated
    private Campus campusOfDeployment;

    @Column
    private LocalDate employmentBegin;

    @Column 
    private LocalDate employmentEnd;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
}
