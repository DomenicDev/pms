package de.hfu.pms.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class DoctoralStudent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PersonalData personalData = new PersonalData();



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

    public PersonalData getPersonalData() {
        return personalData;
    }
}
