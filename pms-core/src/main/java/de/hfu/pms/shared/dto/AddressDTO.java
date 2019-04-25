package de.hfu.pms.shared.dto;

public class AddressDTO {

    private int plz;
    private String location;
    private String country;

    public AddressDTO() {
    }

    public AddressDTO(int plz, String location, String country) {
        this.plz = plz;
        this.location = location;
        this.country = country;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
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
