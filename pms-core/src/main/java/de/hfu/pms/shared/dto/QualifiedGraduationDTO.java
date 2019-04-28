package de.hfu.pms.shared.dto;

import de.hfu.pms.shared.enums.Graduation;

import java.math.BigDecimal;

public class QualifiedGraduationDTO {

    private Graduation graduation;
    private String subjectArea;
    private BigDecimal grade;
    private UniversityDTO qualifiedGraduationUniversity;

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

    public UniversityDTO getQualifiedGraduationUniversity() {
        return qualifiedGraduationUniversity;
    }

    public void setQualifiedGraduationUniversity(UniversityDTO qualifiedGraduationUniversity) {
        this.qualifiedGraduationUniversity = qualifiedGraduationUniversity;
    }
}
