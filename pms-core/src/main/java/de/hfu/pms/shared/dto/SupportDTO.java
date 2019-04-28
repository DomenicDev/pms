package de.hfu.pms.shared.dto;

import java.util.Set;

public class SupportDTO {

    private Set<TravelCostConferenceDTO> conferenceTravelCost;
    private Set<TravelCostUniversityDTO> universityTravelCost;
    private Set<VisitedQualificationDTO> visitedQualifications;
    private Set<ConsultingSupportDTO> consultingSupport;
    private String scholarship;
    private String awards;
    private String miscellaneous;

    public Set<TravelCostConferenceDTO> getConferenceTravelCost() {
        return conferenceTravelCost;
    }

    public void setConferenceTravelCost(Set<TravelCostConferenceDTO> conferenceTravelCost) {
        this.conferenceTravelCost = conferenceTravelCost;
    }

    public Set<TravelCostUniversityDTO> getUniversityTravelCost() {
        return universityTravelCost;
    }

    public void setUniversityTravelCost(Set<TravelCostUniversityDTO> universityTravelCost) {
        this.universityTravelCost = universityTravelCost;
    }

    public Set<VisitedQualificationDTO> getVisitedQualifications() {
        return visitedQualifications;
    }

    public void setVisitedQualifications(Set<VisitedQualificationDTO> visitedQualifications) {
        this.visitedQualifications = visitedQualifications;
    }

    public Set<ConsultingSupportDTO> getConsultingSupport() {
        return consultingSupport;
    }

    public void setConsultingSupport(Set<ConsultingSupportDTO> consultingSupport) {
        this.consultingSupport = consultingSupport;
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
}
