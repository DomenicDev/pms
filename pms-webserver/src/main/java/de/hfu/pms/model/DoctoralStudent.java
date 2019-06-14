package de.hfu.pms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class DoctoralStudent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PersonalData personalData;

    @Embedded
    private QualifiedGraduation qualifiedGraduation;

    @Embedded
    private TargetGraduation targetGraduation;

    @Embedded
    private Employment employment;

    @Embedded
    private Support support;

    @Embedded
    private AlumniState alumniState;

    @Lob // large object
    @Column(columnDefinition="BLOB")
    private byte[] photo; // saved as blob

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_student")
    private Set<Document> documents;

    @Column
    private Boolean anonymized = false;

    /**
     * Default constructor.
     */
    public DoctoralStudent() {
        this.personalData = new PersonalData();
        this.qualifiedGraduation = new QualifiedGraduation();
        this.targetGraduation = new TargetGraduation();
        this.employment = new Employment();
        this.support = new Support();
        this.alumniState = new AlumniState();
        this.documents = new HashSet<>();
    }

    /**
     * Initializes a doctoral student with the specified data.
     * @param personalData
     * @param qualifiedGraduation
     * @param targetGraduation
     * @param employment
     * @param support
     * @param alumniState
     */
    public DoctoralStudent(PersonalData personalData, QualifiedGraduation qualifiedGraduation, TargetGraduation targetGraduation, Employment employment, Support support, AlumniState alumniState, Set<Document> documents) {
        this.personalData = personalData;
        this.qualifiedGraduation = qualifiedGraduation;
        this.targetGraduation = targetGraduation;
        this.employment = employment;
        this.support = support;
        this.alumniState = alumniState;
        this.documents = documents;
    }

    // GETTER AND SETTER


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public QualifiedGraduation getQualifiedGraduation() {
        return qualifiedGraduation;
    }

    public void setQualifiedGraduation(QualifiedGraduation qualifiedGraduation) {
        this.qualifiedGraduation = qualifiedGraduation;
    }

    public TargetGraduation getTargetGraduation() {
        return targetGraduation;
    }

    public void setTargetGraduation(TargetGraduation targetGraduation) {
        this.targetGraduation = targetGraduation;
    }

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    public AlumniState getAlumniState() {
        return alumniState;
    }

    public void setAlumniState(AlumniState alumniState) {
        this.alumniState = alumniState;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public Boolean getAnonymized() {
        return anonymized;
    }

    public void setAnonymized(Boolean anonymized) {
        this.anonymized = anonymized;
    }
}
