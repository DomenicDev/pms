package de.hfu.pms.model;

import de.hfu.pms.shared.enums.Rating;

import javax.persistence.*;
import java.time.LocalDate;

@Embeddable
public class TargetGraduation {

    /**
     * Represents the target degree.
     */
    @Column
    private String targetDegree;

    @Column
    private String nameOfDissertation;

    @Column
    private String internalSupervisor;

    @ManyToOne
    @JoinColumn
    private Faculty facultyHFU;

    @Column
    private String externalSupervisor;

    @Column
    private String externalFaculty;

    @ManyToOne
    @JoinColumn
    private University externalUniversity;

    @Column
    private LocalDate promotionAccepted;

    @Column
    private LocalDate procedureCompleted;

    @Column
    @Enumerated
    private Rating rating;

    @Column
    private Boolean promotionCanceled;

    @Column
    private LocalDate cancelDate;

    @Column
    private String cancelReason;

    @Column
    private Boolean memberOfHFUKolleg;

    @Column
    private LocalDate membershipHFUKollegBegin;

    @Column
    private LocalDate membershipHFUKollegEnd;

    @Column
    private Boolean membershipExtended;

    @Column
    private LocalDate extendedMembershipEndDate;

    @Column
    private Boolean memberOfExternalKolleg;

    @Column
    private String externalProgram;

    @Column
    private LocalDate promotionAdmissionDate;

    @Column
    private LocalDate prognosticatedPromotionDate; // prognostizierter Abschluss

    @Column
    private LocalDate promotionAgreement;


    public String getTargetDegree() {
        return targetDegree;
    }

    public void setTargetDegree(String targetDegree) {
        this.targetDegree = targetDegree;
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

    public Faculty getFacultyHFU() {
        return facultyHFU;
    }

    public void setFacultyHFU(Faculty facultyHFU) {
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

    public University getExternalUniversity() {
        return externalUniversity;
    }

    public void setExternalUniversity(University externalUniversity) {
        this.externalUniversity = externalUniversity;
    }

    public LocalDate getPromotionAccepted() {
        return promotionAccepted;
    }

    public void setPromotionAccepted(LocalDate promotionAccepted) {
        this.promotionAccepted = promotionAccepted;
    }

    public LocalDate getProcedureCompleted() {
        return procedureCompleted;
    }

    public void setProcedureCompleted(LocalDate procedureCompleted) {
        this.procedureCompleted = procedureCompleted;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public LocalDate getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(LocalDate cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public LocalDate getMembershipHFUKollegBegin() {
        return membershipHFUKollegBegin;
    }

    public void setMembershipHFUKollegBegin(LocalDate membershipHFUKollegBegin) {
        this.membershipHFUKollegBegin = membershipHFUKollegBegin;
    }

    public LocalDate getMembershipHFUKollegEnd() {
        return membershipHFUKollegEnd;
    }

    public void setMembershipHFUKollegEnd(LocalDate membershipHFUKollegEnd) {
        this.membershipHFUKollegEnd = membershipHFUKollegEnd;
    }

    public LocalDate getExtendedMembershipEndDate() {
        return extendedMembershipEndDate;
    }

    public void setExtendedMembershipEndDate(LocalDate extendedMembershipEndDate) {
        this.extendedMembershipEndDate = extendedMembershipEndDate;
    }

    public String getExternalProgram() {
        return externalProgram;
    }

    public void setExternalProgram(String externalProgram) {
        this.externalProgram = externalProgram;
    }

    public LocalDate getPromotionAdmissionDate() {
        return promotionAdmissionDate;
    }

    public void setPromotionAdmissionDate(LocalDate promotionAdmissionDate) {
        this.promotionAdmissionDate = promotionAdmissionDate;
    }

    public LocalDate getPrognosticatedPromotionDate() {
        return prognosticatedPromotionDate;
    }

    public void setPrognosticatedPromotionDate(LocalDate prognosticatedPromotionDate) {
        this.prognosticatedPromotionDate = prognosticatedPromotionDate;
    }

    public LocalDate getPromotionAgreement() {
        return promotionAgreement;
    }

    public void setPromotionAgreement(LocalDate promotionAgreement) {
        this.promotionAgreement = promotionAgreement;
    }

    public Boolean getMemberOfHFUKolleg() {
        return memberOfHFUKolleg;
    }

    public void setMemberOfHFUKolleg(Boolean memberOfHFUKolleg) {
        this.memberOfHFUKolleg = memberOfHFUKolleg;
    }

    public Boolean getMemberOfExternalKolleg() {
        return memberOfExternalKolleg;
    }

    public void setMemberOfExternalKolleg(Boolean memberOfExternalKolleg) {
        this.memberOfExternalKolleg = memberOfExternalKolleg;
    }

    public Boolean getMembershipExtended() {
        return membershipExtended;
    }

    public void setMembershipExtended(Boolean membershipExtended) {
        this.membershipExtended = membershipExtended;
    }

    public Boolean getPromotionCanceled() {
        return promotionCanceled;
    }

    public void setPromotionCanceled(Boolean promotionCanceled) {
        this.promotionCanceled = promotionCanceled;
    }
}
