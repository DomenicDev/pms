package de.hfu.pms.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class VisitedQualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate qualificationDate;

    @Column
    private String nameOfQualification;


}
