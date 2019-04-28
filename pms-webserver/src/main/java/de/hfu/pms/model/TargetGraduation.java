package de.hfu.pms.model;

import de.hfu.pms.shared.enums.DoctoralGraduation;
import de.hfu.pms.shared.enums.FacultyHFU;
import de.hfu.pms.shared.enums.Rating;

import javax.persistence.*;
import java.util.Date;

@Embeddable
public class TargetGraduation {

    /**
     * Represents the target degree.
     */
    @Column
    @Enumerated
    private DoctoralGraduation targetDegree;

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
    @Temporal(value = TemporalType.DATE)
    private Date promotionAccepted;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date procedureCompleted;

    @Column
    @Enumerated
    private Rating rating;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date cancelDate;

    @Column
    private String cancelReason;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date membershipHFUKollegStart;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date membershipHFUKollegEnd;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date extendedMembershipEndDate;

    @Column
    private String externalProgramm;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date promotionAdmissionDate;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date prognosticatedPromotionDate; // prognostizierter Abschluss

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date promotionAgreement;


    public DoctoralGraduation getTargetDegree() {
        return targetDegree;
    }

    public void setTargetDegree(DoctoralGraduation targetDegree) {
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

    public Date getMembershipHFUKollegStart() {
        return membershipHFUKollegStart;
    }

    public void setMembershipHFUKollegStart(Date membershipHFUKollegStart) {
        this.membershipHFUKollegStart = membershipHFUKollegStart;
    }

    public Date getMembershipHFUKollegEnd() {
        return membershipHFUKollegEnd;
    }

    public void setMembershipHFUKollegEnd(Date membershipHFUKollegEnd) {
        this.membershipHFUKollegEnd = membershipHFUKollegEnd;
    }

    public Date getExtendedMembershipEndDate() {
        return extendedMembershipEndDate;
    }

    public void setExtendedMembershipEndDate(Date extendedMembershipEndDate) {
        this.extendedMembershipEndDate = extendedMembershipEndDate;
    }

    public String getExternalProgramm() {
        return externalProgramm;
    }

    public void setExternalProgramm(String externalProgramm) {
        this.externalProgramm = externalProgramm;
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
