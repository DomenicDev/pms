package de.hfu.pms.shared.dto;

import java.util.HashSet;
import java.util.Set;

public class SupportDTO {

    private Set<TravelCostConferenceDTO> travelCostConferences = new HashSet<>();
    private Set<TravelCostUniversityDTO> travelCostUniversities = new HashSet<>();
    private Set<VisitedQualificationDTO> visitedQualifications = new HashSet<>();
    private Set<ConsultingSupportDTO> consultingSupports = new HashSet<>();
    private String scholarship;
    private String awards;
    private String miscellaneous;



    public Set<TravelCostConferenceDTO> getTravelCostConferences() {
        return travelCostConferences;
    }

    public void setTravelCostConferences(Set<TravelCostConferenceDTO> travelCostConferences) {
        this.travelCostConferences = travelCostConferences;
    }

    public Set<TravelCostUniversityDTO> getTravelCostUniversities() {
        return travelCostUniversities;
    }

    public void setTravelCostUniversities(Set<TravelCostUniversityDTO> travelCostUniversities) {
        this.travelCostUniversities = travelCostUniversities;
    }

    public Set<VisitedQualificationDTO> getVisitedQualifications() {
        return visitedQualifications;
    }

    public void setVisitedQualifications(Set<VisitedQualificationDTO> visitedQualifications) {
        this.visitedQualifications = visitedQualifications;
    }

    public Set<ConsultingSupportDTO> getConsultingSupports() {
        return consultingSupports;
    }

    public void setConsultingSupports(Set<ConsultingSupportDTO> consultingSupports) {
        this.consultingSupports = consultingSupports;
    }

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

    @Override
    public String toString() {
        return "SupportDTO{" +
                "travelCostConferences=" + travelCostConferences +
                ", travelCostUniversities=" + travelCostUniversities +
                ", visitedQualifications=" + visitedQualifications +
                ", consultingSupports=" + consultingSupports +
                ", scholarship='" + scholarship + '\'' +
                ", awards='" + awards + '\'' +
                ", miscellaneous='" + miscellaneous + '\'' +
                '}';
    }
}
