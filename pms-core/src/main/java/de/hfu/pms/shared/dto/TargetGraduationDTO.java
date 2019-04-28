package de.hfu.pms.shared.dto;

import de.hfu.pms.shared.enums.FacultyHFU;
import de.hfu.pms.shared.enums.Rating;

import java.util.Date;

public class TargetGraduationDTO {

    private String targetGraduationDegree;
    private String nameOfDissertation;
    private String internalSupervisor;
    private FacultyHFU facultyHFU;
    private String externalSupervisor;
    private String externalFaculty;
    private UniversityDTO externalUniversity;
    private Date promotionAccepted;
    private Date procedureCompleted;
    private Rating rating;
    private Date cancelDate;
    private String cancelReason;
    private Date membershipHFUKollegBegin;
    private Date membershipHFUKollegEnd;
    private Date extendedMembershipEnd;
    private String externalProgram;
    private Date promotionAdmissionDate;
    private Date prognosticatedPromotionDate;
    private Date promotionAgreement;


    public String getTargetGraduationDegree() {
        return targetGraduationDegree;
    }

    public void setTargetGraduationDegree(String targetGraduationDegree) {
        this.targetGraduationDegree = targetGraduationDegree;
    }

    public String getNameOfDissertation() {
        return nameOfDissertation;
    }

    public void setNameOfDissertation(String nameOfDissertation) {
        this.nameOfDissertation = nameOfDissertation;
    }

    public String getInternalSupervisor() {
        return internalSupervisor;
    }

    public void setInternalSupervisor(String internalSupervisor) {
        this.internalSupervisor = internalSupervisor;
    }

    public FacultyHFU getFacultyHFU() {
        return facultyHFU;
    }

    public void setFacultyHFU(FacultyHFU facultyHFU) {
        this.facultyHFU = facultyHFU;
    }

    public String getExternalSupervisor() {
        return externalSupervisor;
    }

    public void setExternalSupervisor(String externalSupervisor) {
        this.externalSupervisor = externalSupervisor;
    }

    public String getExternalFaculty() {
        return externalFaculty;
    }

    public void setExternalFaculty(String externalFaculty) {
        this.externalFaculty = externalFaculty;
    }

    public UniversityDTO getExternalUniversity() {
        return externalUniversity;
    }

    public void setExternalUniversity(UniversityDTO externalUniversity) {
        this.externalUniversity = externalUniversity;
    }

    public Date getPromotionAccepted() {
        return promotionAccepted;
    }

    public void setPromotionAccepted(Date promotionAccepted) {
        this.promotionAccepted = promotionAccepted;
    }

    public Date getProcedureCompleted() {
        return procedureCompleted;
    }

    public void setProcedureCompleted(Date procedureCompleted) {
        this.procedureCompleted = procedureCompleted;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Date getMembershipHFUKollegBegin() {
        return membershipHFUKollegBegin;
    }

    public void setMembershipHFUKollegBegin(Date membershipHFUKollegBegin) {
        this.membershipHFUKollegBegin = membershipHFUKollegBegin;
    }

    public Date getMembershipHFUKollegEnd() {
        return membershipHFUKollegEnd;
    }

    public void setMembershipHFUKollegEnd(Date membershipHFUKollegEnd) {
        this.membershipHFUKollegEnd = membershipHFUKollegEnd;
    }

    public Date getExtendedMembershipEnd() {
        return extendedMembershipEnd;
    }

    public void setExtendedMembershipEnd(Date extendedMembershipEnd) {
        this.extendedMembershipEnd = extendedMembershipEnd;
    }

    public String getExternalProgram() {
        return externalProgram;
    }

    public void setExternalProgram(String externalProgram) {
        this.externalProgram = externalProgram;
    }

    public Date getPromotionAdmissionDate() {
        return promotionAdmissionDate;
    }

    public void setPromotionAdmissionDate(Date promotionAdmissionDate) {
        this.promotionAdmissionDate = promotionAdmissionDate;
    }

    public Date getPrognosticatedPromotionDate() {
        return prognosticatedPromotionDate;
    }

    public void setPrognosticatedPromotionDate(Date prognosticatedPromotionDate) {
        this.prognosticatedPromotionDate = prognosticatedPromotionDate;
    }

    public Date getPromotionAgreement() {
        return promotionAgreement;
    }

    public void setPromotionAgreement(Date promotionAgreement) {
        this.promotionAgreement = promotionAgreement;
    }
}
