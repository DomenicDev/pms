package de.hfu.pms.shared.dto;

import de.hfu.pms.shared.enums.Graduation;

public class QualifiedGraduationDTO {

    private Graduation graduation;
    private String subjectArea;
    private String grade;
    private UniversityDTO gradedUniversity;

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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public UniversityDTO getGradedUniversity() {
        return gradedUniversity;
    }

    public void setGradedUniversity(UniversityDTO gradedUniversity) {
        this.gradedUniversity = gradedUniversity;
    }

    @Override
    public String toString() {
        return "QualifiedGraduationDTO{" +
                "graduation=" + graduation +
                ", subjectArea='" + subjectArea + '\'' +
                ", grade='" + grade + '\'' +
                ", gradedUniversity=" + gradedUniversity +
                '}';
    }
}
