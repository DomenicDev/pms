package de.hfu.pms.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AlumniState {

    @Column
    private String jobTitle;

    @Column
    private String employer;

    @Column
    private boolean agreementNews;

    @Column
    private boolean agreementEvaluation;

}
