package de.hfu.pms.model;

import javax.persistence.*;

@Entity
@Table
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String location;

    @Column
    private String country;
}
