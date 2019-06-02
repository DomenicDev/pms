package de.hfu.pms.shared.dto;

import java.util.Objects;

public class FacultyDTO {

    private Long id;
    private String facultyName;

    public FacultyDTO() {
    }

    public FacultyDTO(Long id, String facultyName) {
        this.id = id;
        this.facultyName = facultyName;
    }

    public FacultyDTO(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public String toString() {
        return facultyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacultyDTO that = (FacultyDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(facultyName, that.facultyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, facultyName);
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

