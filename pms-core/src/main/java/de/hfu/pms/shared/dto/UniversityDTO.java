package de.hfu.pms.shared.dto;

import java.util.Objects;

public class UniversityDTO {

    private Long id;
    private String name;
    private String location;
    private String country;
    private String abbreviation;
    private String contact;

    public UniversityDTO() {
    }

    public UniversityDTO(Long id, String name, String location, String country, String abbreviation, String contact) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.country = country;
        this.abbreviation = abbreviation;
        this.contact = contact;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniversityDTO that = (UniversityDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(location, that.location) &&
                Objects.equals(country, that.country) &&
                Objects.equals(abbreviation, that.abbreviation) &&
                Objects.equals(contact, that.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, location, country, abbreviation,contact);
    }

    @Override
    public String toString() {
        return "UniversityDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", country='" + country + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
