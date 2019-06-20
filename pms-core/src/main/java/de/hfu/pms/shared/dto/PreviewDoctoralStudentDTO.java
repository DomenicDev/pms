package de.hfu.pms.shared.dto;

import de.hfu.pms.shared.enums.Gender;

import java.util.Objects;

public class PreviewDoctoralStudentDTO {

    private Long id;
    private String foreName;
    private String name;
    private FacultyDTO faculty;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private boolean active;
    private boolean anonymized;
    private boolean memberHFUCollege;

    public PreviewDoctoralStudentDTO() {
    }

    public PreviewDoctoralStudentDTO(String forname, String lastname,FacultyDTO faculty, String email, String phoneNumber, Gender gender){
        this.foreName = forname;
        this.name = lastname;
        this.faculty = faculty;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public PreviewDoctoralStudentDTO(Long id, String name, String foreName, FacultyDTO faculty, String email, String phoneNumber, Gender gender) {
        this.id = id;
        this.name = name;
        this.foreName = foreName;
        this.faculty = faculty;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getForeName() {
        return foreName;
    }

    public void setForeName(String foreName) {
        this.foreName = foreName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FacultyDTO getFaculty() { return faculty; }

    public void setFaculty(FacultyDTO faculty){ this.faculty = faculty; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAnonymized() {
        return anonymized;
    }

    public void setAnonymized(boolean anonymized) {
        this.anonymized = anonymized;
    }

    public boolean isMemberHFUCollege() {
        return memberHFUCollege;
    }

    public void setMemberHFUCollege(boolean memberHFUCollege) {
        this.memberHFUCollege = memberHFUCollege;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreviewDoctoralStudentDTO that = (PreviewDoctoralStudentDTO) o;
        return active == that.active &&
                anonymized == that.anonymized &&
                memberHFUCollege == that.memberHFUCollege &&
                Objects.equals(id, that.id) &&
                Objects.equals(foreName, that.foreName) &&
                Objects.equals(name, that.name) &&
                Objects.equals(faculty, that.faculty) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                gender == that.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, foreName, name, faculty, email, phoneNumber, gender, active, anonymized, memberHFUCollege);
    }

    @Override
    public String toString() {
        return "PreviewDoctoralStudentDTO{" +
                "id=" + id +
                ", foreName='" + foreName + '\'' +
                ", name='" + name + '\'' +
                ", faculty=" + faculty +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender +
                '}';
    }
}
