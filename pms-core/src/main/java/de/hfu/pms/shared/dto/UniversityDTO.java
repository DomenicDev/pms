package de.hfu.pms.shared.dto;

public class UniversityDTO {

    private Long id;
    private String name;
    private String location;
    private String country;
    private String abbreviation;

    public UniversityDTO() {
    }

    public UniversityDTO(Long id, String name, String location, String country, String abbreviation) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.country = country;
        this.abbreviation = abbreviation;
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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return "UniversityDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", country='" + country + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                '}';
    }
}
