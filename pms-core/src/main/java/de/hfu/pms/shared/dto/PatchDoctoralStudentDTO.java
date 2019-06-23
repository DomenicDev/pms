package de.hfu.pms.shared.dto;

import java.util.Collection;

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

    private boolean changedPhoto;

    // profile photo
    private PhotoDTO photo;

    // documents
    private Collection<DocumentDTO> documentsToAdd;

    private Collection<Long> documentsToRemove;

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

    /**
     * Set the control flag if photo has changed.
     * If this flag is set and the photo byte array is null,
     * this implies a delete action.
     * @param changedPhoto
     */
    public void setChangedPhoto(boolean changedPhoto) {
        this.changedPhoto = changedPhoto;
    }

    public boolean isChangedPhoto() {
        return changedPhoto;
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

    public PhotoDTO getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoDTO photo) {
        this.photo = photo;
    }

    public Collection<DocumentDTO> getDocumentsToAdd() {
        return documentsToAdd;
    }

    public void setDocumentsToAdd(Collection<DocumentDTO> documentsToAdd) {
        this.documentsToAdd = documentsToAdd;
    }

    public Collection<Long> getDocumentsToRemove() {
        return documentsToRemove;
    }

    public void setDocumentsToRemove(Collection<Long> documentsToRemove) {
        this.documentsToRemove = documentsToRemove;
    }
}
