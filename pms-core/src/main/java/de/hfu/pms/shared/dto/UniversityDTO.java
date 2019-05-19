package de.hfu.pms.shared.dto;

public class UniversityDTO {

    private Long id;
    private String name;
    private String location;
    private String country;

    public UniversityDTO() {
    }

    public UniversityDTO(Long id, String name, String location, String country) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
