package de.hfu.pms.shared.dto;

public class FacultyDTO {

    private Long id;
    private String facultyName;

    public FacultyDTO() {
    }

    public FacultyDTO(Long id, String facultyName) {
        this.id = id;
        this.facultyName = facultyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
}

