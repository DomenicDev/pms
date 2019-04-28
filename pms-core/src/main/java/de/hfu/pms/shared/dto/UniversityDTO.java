package de.hfu.pms.shared.dto;

public class UniversityDTO {

    private String name;
    private String location;
    private String country;

    public UniversityDTO() {
    }

    public UniversityDTO(String name, String location, String country) {
        this.name = name;
        this.location = location;
        this.country = country;
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
