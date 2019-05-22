package de.hfu.pms.shared.dto;

import de.hfu.pms.shared.enums.FamilyStatus;
import de.hfu.pms.shared.enums.Gender;
import de.hfu.pms.shared.enums.Salutation;

import java.time.LocalDate;
import java.util.Arrays;

public class PersonalDataDTO {

    private String forename;
    private String lastName;
    private String formerLastName;
    private Salutation salutation;
    private String title;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String nationality;
    private FamilyStatus familyStatus;
    private Integer numberOfChildren;
    private AddressDTO address = new AddressDTO();
    private String telephone;
    private String email;
    private byte[] photo;

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "PersonalDataDTO{" +
                "forename='" + forename + '\'' +
                ", lastName='" + lastName + '\'' +
                ", formerLastName='" + formerLastName + '\'' +
                ", salutation=" + salutation +
                ", title='" + title + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", nationality='" + nationality + '\'' +
                ", familyStatus=" + familyStatus +
                ", numberOfChildren=" + numberOfChildren +
                ", address=" + address +
                ", telephone='" + telephone + '\'' +
                ", email=" + email +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
