package de.hfu.pms.model;

import de.hfu.pms.shared.enums.FacultyHFU;
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

    @Column
    @Enumerated
    private FacultyHFU facultyHFU;

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
    private LocalDate cancelDate;

    @Column
    private String cancelReason;

    @Column
    private LocalDate membershipHFUKollegStart;

    @Column
    private LocalDate membershipHFUKollegEnd;

    @Column
    private LocalDate extendedMembershipEndDate;

    @Column
    private String externalProgramm;

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

    public LocalDate getMembershipHFUKollegStart() {
        return membershipHFUKollegStart;
    }

    public void setMembershipHFUKollegStart(LocalDate membershipHFUKollegStart) {
        this.membershipHFUKollegStart = membershipHFUKollegStart;
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

    public String getExternalProgramm() {
        return externalProgramm;
    }

    public void setExternalProgramm(String externalProgramm) {
        this.externalProgramm = externalProgramm;
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
}
