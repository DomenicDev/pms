package de.hfu.pms.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

    @Column
    private String plz;

    @Column
    private String name;

    @Column
    private String country;

    public Address() {
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
