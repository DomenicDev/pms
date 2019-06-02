package de.hfu.pms.shared.dto;

import de.hfu.pms.shared.enums.Gender;

public class PreviewDoctoralStudentDTO {

    private Long id;
    private String foreName;
    private String name;
    private FacultyDTO faculty;
    private String email;
    private String phoneNumber;
    private Gender gender;

    public PreviewDoctoralStudentDTO() {
    }

    public PreviewDoctoralStudentDTO(String forname, String lastname){
        this.foreName = forname;
        this.name = lastname;
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
