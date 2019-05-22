package de.hfu.pms.shared.dto;

import java.time.LocalDate;

public class VisitedQualificationDTO {

    private Long id;
    private String nameOfQualification;
    private LocalDate qualificationDate;

    public VisitedQualificationDTO() {
    }

    public VisitedQualificationDTO(String nameOfQualification, LocalDate qualificationDate) {
        this.nameOfQualification = nameOfQualification;
        this.qualificationDate = qualificationDate;
    }

    public VisitedQualificationDTO(Long id, String nameOfQualification, LocalDate qualificationDate) {
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

    public LocalDate getQualificationDate() {
        return qualificationDate;
    }

    public void setQualificationDate(LocalDate qualificationDate) {
        this.qualificationDate = qualificationDate;
    }

    @Override
    public String toString() {
        return "VisitedQualificationDTO{" +
                "id=" + id +
                ", nameOfQualification='" + nameOfQualification + '\'' +
                ", qualificationDate=" + qualificationDate +
                '}';
    }
}
