package de.hfu.pms.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class VisitedQualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate qualificationDate;

    @Column
    private String nameOfQualification;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getQualificationDate() {
        return qualificationDate;
    }

    public void setQualificationDate(LocalDate qualificationDate) {
        this.qualificationDate = qualificationDate;
    }

    public String getNameOfQualification() {
        return nameOfQualification;
    }

    public void setNameOfQualification(String nameOfQualification) {
        this.nameOfQualification = nameOfQualification;
    }
}
