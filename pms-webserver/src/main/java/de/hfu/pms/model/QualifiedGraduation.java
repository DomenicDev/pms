package de.hfu.pms.model;

import de.hfu.pms.shared.enums.Graduation;

import javax.persistence.*;
import java.math.BigDecimal;

@Embeddable
public class QualifiedGraduation {

    @Column
    @Enumerated
    private Graduation graduation;

    @Column
    private String subjectArea;


    /**
     * Grades must have the format d.dd (example: 1.40)
     */
    @Column
    private BigDecimal grade; // TODO: write tests for this attribute

    @ManyToOne(targetEntity = University.class)
    private University gradedUniversity;
}
