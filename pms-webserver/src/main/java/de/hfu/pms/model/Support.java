package de.hfu.pms.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Set;

@Embeddable
public class Support {

    @OneToMany
    @JoinColumn(name = "fk_student")
    private Set<TravelCostConference> travelCostConferences;

    @OneToMany
    @JoinColumn(name = "fk_student")
    private Set<TravelCostUniversity> travelCostUniversities;

    @OneToMany
    @JoinColumn(name = "fk_student")
    private Set<VisitedQualification> visitedQualifications;

    @OneToMany
    @JoinColumn(name = "fk_student")
    private Set<ConsultingSupport> consultingSupports;

    @Column
    private String scholarship;

    @Column
    private String awards;

    @Column
    private String miscellaneous;


    // GETTER AND SETTERS

    public String getScholarship() {
        return scholarship;
    }

    public void setScholarship(String scholarship) {
        this.scholarship = scholarship;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getMiscellaneous() {
        return miscellaneous;
    }

    public void setMiscellaneous(String miscellaneous) {
        this.miscellaneous = miscellaneous;
    }


    public Set<TravelCostConference> getTravelCostConferences() {
        return travelCostConferences;
    }

    public void setTravelCostConferences(Set<TravelCostConference> travelCostConferences) {
        this.travelCostConferences = travelCostConferences;
    }


    public Set<TravelCostUniversity> getTravelCostUniversities() {
        return travelCostUniversities;
    }

    public void setTravelCostUniversities(Set<TravelCostUniversity> travelCostUniversities) {
        this.travelCostUniversities = travelCostUniversities;
    }

    public Set<VisitedQualification> getVisitedQualifications() {
        return visitedQualifications;
    }

    public void setVisitedQualifications(Set<VisitedQualification> visitedQualifications) {
        this.visitedQualifications = visitedQualifications;
    }

    public Set<ConsultingSupport> getConsultingSupports() {
        return consultingSupports;
    }

    public void setConsultingSupports(Set<ConsultingSupport> consultingSupports) {
        this.consultingSupports = consultingSupports;
    }
}
