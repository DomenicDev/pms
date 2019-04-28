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

    @Embedded
    private Address mailingAdress;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFormerLastName() {
        return formerLastName;
    }

    public void setFormerLastName(String formerLastName) {
        this.formerLastName = formerLastName;
    }

    public Salutation getSalutation() {
        return salutation;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public FamilyStatus getFamilyStatus() {
        return familyStatus;
    }

    public void setFamilyStatus(FamilyStatus familyStatus) {
        this.familyStatus = familyStatus;
    }

    public Integer getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(Integer numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public Address getMailingAdress() {
        return mailingAdress;
    }

    public void setMailingAdress(Address mailingAdress) {
        this.mailingAdress = mailingAdress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
