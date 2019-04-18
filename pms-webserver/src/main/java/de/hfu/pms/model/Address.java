package de.hfu.pms.model;

import javax.persistence.*;

@Embeddable
public class Address {

    @Column
    private int plz;

    @Column
    private String name;

    @Column
    private String country;
}
