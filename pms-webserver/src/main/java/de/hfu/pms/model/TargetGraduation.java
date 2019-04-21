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


}
