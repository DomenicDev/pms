package de.hfu.pms.shared.dto;

import java.util.Collection;

/**
 * Used for creating a new doctoral student entity.
 */
public class CreateDoctoralStudentDTO {

    private PersonalDataDTO personalData;
    private QualifiedGraduationDTO qualifiedGraduation;
    private TargetGraduationDTO targetGraduation;
    private EmploymentDTO employment;
    private SupportDTO support;
    private AlumniStateDTO alumniState;
    private byte[] photo;
    private Collection<DocumentDTO> documents;

    public CreateDoctoralStudentDTO() {
        // empty no-args constructor
    }


    public PersonalDataDTO getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalDataDTO personalData) {
        this.personalData = personalData;
    }

    public QualifiedGraduationDTO getQualifiedGraduation() {
        return qualifiedGraduation;
    }

    public void setQualifiedGraduation(QualifiedGraduationDTO qualifiedGraduation) {
        this.qualifiedGraduation = qualifiedGraduation;
    }

    public TargetGraduationDTO getTargetGraduation() {
        return targetGraduation;
    }

    public void setTargetGraduation(TargetGraduationDTO targetGraduation) {
        this.targetGraduation = targetGraduation;
    }

    public EmploymentDTO getEmployment() {
        return employment;
    }

    public void setEmployment(EmploymentDTO employment) {
        this.employment = employment;
    }

    public SupportDTO getSupport() {
        return support;
    }

    public void setSupport(SupportDTO support) {
        this.support = support;
    }

    public AlumniStateDTO getAlumniState() {
        return alumniState;
    }

    public void setAlumniState(AlumniStateDTO alumniState) {
        this.alumniState = alumniState;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Collection<DocumentDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(Collection<DocumentDTO> documents) {
        this.documents = documents;
    }
}
