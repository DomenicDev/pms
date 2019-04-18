package de.hfu.pms.model;

import de.hfu.pms.shared.enums.Campus;
import de.hfu.pms.shared.enums.EmploymentLocation;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

@Embeddable
public class Employment {

    @Column
    @Enumerated
    private EmploymentLocation employmentLocation;

    @Column
    private String kindOfEmployment;

    @Column
    @Enumerated
    private Campus campusOfDeployment;

    @Column
    private boolean preEmploymentTimeToBeCharged;

}
