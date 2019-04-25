package de.hfu.pms.model;

import de.hfu.pms.shared.enums.Graduation;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Embeddable
public class QualifiedGraduation {

    @Column
    @Enumerated
    private Graduation graduation;

    @Column
    private String subjectArea;


    /**
     * Grades must have the format d.dd (example: 1.40)
     */
    @Column
    private BigDecimal grade; // TODO: write tests for this attribute

    @ManyToOne(targetEntity = University.class)
    private University gradedUniversity;

    public Graduation getGraduation() {
        return graduation;
    }

    public void setGraduation(Graduation graduation) {
        this.graduation = graduation;
    }

    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    public University getGradedUniversity() {
        return gradedUniversity;
    }

    public void setGradedUniversity(University gradedUniversity) {
        this.gradedUniversity = gradedUniversity;
    }
}
