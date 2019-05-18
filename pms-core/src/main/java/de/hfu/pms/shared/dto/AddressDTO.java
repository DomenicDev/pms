package de.hfu.pms.shared.dto;

public class AddressDTO {

    private String street;
    private String plz;
    private String location;
    private String country;

    public AddressDTO() {
    }

    public AddressDTO(String street, String plz, String location, String country) {
        this.street = street;
        this.plz = plz;
        this.location = location;
        this.country = country;
    }

    public String getStreet() { return street; }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
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
