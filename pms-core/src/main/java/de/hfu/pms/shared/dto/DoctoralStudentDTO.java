package de.hfu.pms.shared.dto;

public class DoctoralStudentDTO {

    // IDENTIFICATION NUMBER
    private Long id;

    // PERSONAL DATA
    private PersonalDataDTO personalData;

    // QUALIFIED GRADUATION
    private QualifiedGraduationDTO qualifiedGraduation;

    // TARGET GRADUATION
    private TargetGraduationDTO targetGraduation;

    // EMPLOYMENT
    private EmploymentDTO employment;

    // SUPPORT
    private SupportDTO support;

    // ALUMNI STATE
    private AlumniStateDTO alumniState;

    public DoctoralStudentDTO() {
        this.personalData = new PersonalDataDTO();
        this.qualifiedGraduation = new QualifiedGraduationDTO();
        this.targetGraduation = new TargetGraduationDTO();
        this.employment = new EmploymentDTO();
        this.support = new SupportDTO();
        this.alumniState = new AlumniStateDTO();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "DoctoralStudentDTO{" +
                "id=" + id +
                ", personalData=" + personalData +
                ", qualifiedGraduation=" + qualifiedGraduation +
                ", targetGraduation=" + targetGraduation +
                ", employment=" + employment +
                ", support=" + support +
                ", alumniState=" + alumniState +
                '}';
    }
}
