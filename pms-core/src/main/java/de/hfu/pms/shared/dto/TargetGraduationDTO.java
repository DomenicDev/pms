package de.hfu.pms.shared.dto;

import de.hfu.pms.shared.enums.Rating;

import java.time.LocalDate;

public class TargetGraduationDTO {

    private String targetGraduationDegree;
    private String nameOfDissertation;
    private String internalSupervisor;
    private FacultyDTO facultyHFU;
    private String externalSupervisor;
    private String externalFaculty;
    private UniversityDTO externalUniversity;
    private LocalDate promotionAccepted;
    private LocalDate procedureCompleted;
    private Rating rating;
    private boolean promotionCanceled;
    private LocalDate cancelDate;
    private String cancelReason;
    private boolean memberOfHFUKolleg;
    private LocalDate membershipHFUKollegBegin;
    private LocalDate membershipHFUKollegEnd;
    private boolean membershipExtended;
    private LocalDate extendedMembershipEnd;
    private boolean memberOfExternalKolleg;
    private String externalProgram;
    private LocalDate promotionAdmissionDate;
    private LocalDate prognosticatedPromotionDate;
    private LocalDate promotionAgreement;


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

    public FacultyDTO getFacultyHFU() {
        return facultyHFU;
    }

    public void setFacultyHFU(FacultyDTO facultyHFU) {
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

    public boolean isPromotionCanceled() {
        return promotionCanceled;
    }

    public void setPromotionCanceled(boolean promotionCanceled) {
        this.promotionCanceled = promotionCanceled;
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

    public LocalDate getExtendedMembershipEnd() {
        return extendedMembershipEnd;
    }

    public void setExtendedMembershipEnd(LocalDate extendedMembershipEnd) {
        this.extendedMembershipEnd = extendedMembershipEnd;
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

    public boolean isMemberOfHFUKolleg() {
        return memberOfHFUKolleg;
    }

    public void setMemberOfHFUKolleg(boolean memberOfHFUKolleg) {
        this.memberOfHFUKolleg = memberOfHFUKolleg;
    }

    public boolean isMemberOfExternalKolleg() {
        return memberOfExternalKolleg;
    }

    public void setMemberOfExternalKolleg(boolean memberOfExternalKolleg) {
        this.memberOfExternalKolleg = memberOfExternalKolleg;
    }

    public boolean isMembershipExtended() {
        return membershipExtended;
    }

    public void setMembershipExtended(boolean membershipExtended) {
        this.membershipExtended = membershipExtended;
    }

    @Override
    public String toString() {
        return "TargetGraduationDTO{" +
                "targetGraduationDegree='" + targetGraduationDegree + '\'' +
                ", nameOfDissertation='" + nameOfDissertation + '\'' +
                ", internalSupervisor='" + internalSupervisor + '\'' +
                ", facultyHFU=" + facultyHFU.getFacultyName() +
                ", externalSupervisor='" + externalSupervisor + '\'' +
                ", externalFaculty='" + externalFaculty + '\'' +
                ", externalUniversity=" + externalUniversity +
                ", promotionAccepted=" + promotionAccepted +
                ", procedureCompleted=" + procedureCompleted +
                ", rating=" + rating +
                ", cancelDate=" + cancelDate +
                ", cancelReason='" + cancelReason + '\'' +
                ", membershipHFUKollegBegin=" + membershipHFUKollegBegin +
                ", membershipHFUKollegEnd=" + membershipHFUKollegEnd +
                ", extendedMembershipEnd=" + extendedMembershipEnd +
                ", externalProgram='" + externalProgram + '\'' +
                ", promotionAdmissionDate=" + promotionAdmissionDate +
                ", prognosticatedPromotionDate=" + prognosticatedPromotionDate +
                ", promotionAgreement=" + promotionAgreement +
                '}';
    }
}
