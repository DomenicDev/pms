package de.hfu.pms.shared.dto;

import java.util.Date;

public class VisitedQualificationDTO {

    private Long id;
    private String nameOfQualification;
    private Date qualificationDate;

    public VisitedQualificationDTO() {
    }

    public VisitedQualificationDTO(String nameOfQualification, Date qualificationDate) {
        this.nameOfQualification = nameOfQualification;
        this.qualificationDate = qualificationDate;
    }

    public VisitedQualificationDTO(Long id, String nameOfQualification, Date qualificationDate) {
        this.id = id;
        this.nameOfQualification = nameOfQualification;
        this.qualificationDate = qualificationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfQualification() {
        return nameOfQualification;
    }

    public void setNameOfQualification(String nameOfQualification) {
        this.nameOfQualification = nameOfQualification;
    }

    public Date getQualificationDate() {
        return qualificationDate;
    }

    public void setQualificationDate(Date qualificationDate) {
        this.qualificationDate = qualificationDate;
    }
}
