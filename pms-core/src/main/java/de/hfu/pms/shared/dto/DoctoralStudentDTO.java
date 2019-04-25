package de.hfu.pms.shared.dto;

import de.hfu.pms.shared.enums.*;

import java.math.BigDecimal;
import java.util.Date;

public class DoctoralStudentDTO {

    // IDENTIFICATION NUMBER
    private int id;

    // PERSONAL DATA
    private String forename;
    private String lastName;
    private String formerLastName;
    private Salutation salutation;
    private String title;
    private Gender gender;
    private Date dateOfBirth;
    private String nationality;
    private FamilyStatus familyStatus;
    private Integer numberOfChildren;
    private AddressDTO address;
    private String telephone;
    private byte[] photo;

    // QUALIFIED GRADUATION
    private Graduation graduation;
    private String subjectArea;
    private BigDecimal grade;
    private UniversityDTO qualifiedGraduationUniversity;

    // TARGET GRADUATION
    private String targetGraduationDegree;
    private String nameOfDissertation;
    private String internalSupervisor;
    private FacultyHFU facultyHFU;
    private String externalSupervisor;
    private String externalFaculty;
    private UniversityDTO externalUniversity;
    private Date promotionAccepted;
    private Date procedureCompleted;
    private Rating rating;
    private Date cancelDate;
    private String cancelReason;
    private Date membershipHFUKollegBegin;
    private Date membershipHFUKollegEnd;
    private Date extendedMembershipEnd;
    private String externalProgram;
    private Date promotionAdmissionDate;
    private Date prognosticatedPromotionDate;
    private Date promotionAgreement;

    // EMPLOYMENT
    private EmploymentDTO employment;

    // SUPPORT
    private SupportDTO support;

    // ALUMNI STATE
    private String jobTitle;
    private String employer;
    private boolean agreementNews;
    private boolean agreementEvaluation;


}
