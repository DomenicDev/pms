package de.hfu.pms.model;

import de.hfu.pms.shared.enums.FamilyStatus;
import de.hfu.pms.shared.enums.Gender;
import de.hfu.pms.shared.enums.Salutation;

import javax.persistence.*;
import java.util.Date;

@Embeddable
public class PersonalData {

    @Column
    private String forename;

    @Column
    private String lastName;

    @Column
    private String formerLastName;

    @Column
    @Enumerated
    private Salutation salutation;

    @Column
    private String title;

    @Column
    @Enumerated
    private Gender gender;

    @Column
    @Temporal(TemporalType.DATE) // to map java.util.Date to SQL-format
    private Date dateOfBirth;

    @Column
    private String nationality;

    @Column
    @Enumerated
    private FamilyStatus familyStatus;

    @Column
    private Integer numberOfChildren;

    @Column
    private String email;

    @Column
    private String telephone;

    @Column
    private byte[] photo; // saved as blob

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getForename() {
        return forename;
    }
}
