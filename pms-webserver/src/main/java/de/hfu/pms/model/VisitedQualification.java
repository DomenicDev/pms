package de.hfu.pms.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class VisitedQualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date qualificationDate;

    @Column
    private String nameOfQualification;


}
