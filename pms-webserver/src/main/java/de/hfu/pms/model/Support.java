package de.hfu.pms.model;

import de.hfu.pms.model.TravelCostSupport;
import de.hfu.pms.model.VisitedQualification;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

@Embeddable
public class Support {

    @OneToMany
    private Set<TravelCostSupport> travelCostSupports;

    @OneToMany
    private Set<VisitedQualification> visitedQualifications;

    // todo ---> check if only one consulting entry is enough
    @Column
    private Date consultingDate;

    @Column
    private String consultingType;

    @Column
    private int consultingDuration; // in minutes

    @Column
    private String scholarship;

    @Column
    private String awards;

    @Column
    private String miscellaneous;


}
