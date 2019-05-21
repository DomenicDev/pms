package de.hfu.pms.shared.dto;

import de.hfu.pms.shared.enums.FacultyHFU;
import de.hfu.pms.shared.enums.Gender;

public class PreviewDoctoralStudentDTO {

    private Long id;
    private String foreName;
    private String name;
    private FacultyHFU faculty;
    private String email;
    private String phoneNumber;
    private Gender gender;

    public PreviewDoctoralStudentDTO(Long id, String foreName, String name, FacultyHFU faculty, String email, String phoneNumber, Gender gender) {
        this.id = id;
        this.foreName = foreName;
        this.name = name;
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

    public String getforeName() {
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

    public FacultyHFU getFaculty() { return faculty; }

    public void setFaculty(FacultyHFU faculty){ this.faculty = faculty; }

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

}
