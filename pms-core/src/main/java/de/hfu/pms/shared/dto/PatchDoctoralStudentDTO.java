package de.hfu.pms.shared.dto;

/**
 * This transfer object is used for partially update
 * the doctoral student entity.
 *
 * Fields that are equal to null are going to be ignored.
 */
public class PatchDoctoralStudentDTO {

    // IDENTIFICATION NUMBER
    private Long id;

    private PersonalDataDTO patchedPersonalData;

    // QUALIFIED GRADUATION
    private QualifiedGraduationDTO patchedQualifiedGraduation;

    // TARGET GRADUATION
    private TargetGraduationDTO patchedTargetGraduation;

    // EMPLOYMENT
    private EmploymentDTO patchedEmployment;

    // SUPPORT
    private SupportDTO patchedSupport;

    // ALUMNI STATE
    private AlumniStateDTO patchedAlumniState;

    // profile photo
    private byte[] photo;

    public PatchDoctoralStudentDTO() {
    }

    public PatchDoctoralStudentDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonalDataDTO getPatchedPersonalData() {
        return patchedPersonalData;
    }

    public void setPatchedPersonalData(PersonalDataDTO patchedPersonalData) {
        this.patchedPersonalData = patchedPersonalData;
    }

    public QualifiedGraduationDTO getPatchedQualifiedGraduation() {
        return patchedQualifiedGraduation;
    }

    public void setPatchedQualifiedGraduation(QualifiedGraduationDTO patchedQualifiedGraduation) {
        this.patchedQualifiedGraduation = patchedQualifiedGraduation;
    }

    public TargetGraduationDTO getPatchedTargetGraduation() {
        return patchedTargetGraduation;
    }

    public void setPatchedTargetGraduation(TargetGraduationDTO patchedTargetGraduation) {
        this.patchedTargetGraduation = patchedTargetGraduation;
    }

    public EmploymentDTO getPatchedEmployment() {
        return patchedEmployment;
    }

    public void setPatchedEmployment(EmploymentDTO patchedEmployment) {
        this.patchedEmployment = patchedEmployment;
    }

    public SupportDTO getPatchedSupport() {
        return patchedSupport;
    }

    public void setPatchedSupport(SupportDTO patchedSupport) {
        this.patchedSupport = patchedSupport;
    }

    public AlumniStateDTO getPatchedAlumniState() {
        return patchedAlumniState;
    }

    public void setPatchedAlumniState(AlumniStateDTO patchedAlumniState) {
        this.patchedAlumniState = patchedAlumniState;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
