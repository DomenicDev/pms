package de.hfu.pms.shared.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TravelCostConferenceDTO {

    private Long id;
    private LocalDate date;
    private String location;
    private BigDecimal sum;
    private String conferenceTitle;

    public TravelCostConferenceDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public String getConferenceTitle() {
        return conferenceTitle;
    }

    public void setConferenceTitle(String conferenceTitle) {
        this.conferenceTitle = conferenceTitle;
    }

    @Override
    public String toString() {
        return "TravelCostConferenceDTO{" +
                "id=" + id +
                ", date=" + date +
                ", location='" + location + '\'' +
                ", sum=" + sum +
                ", conferenceTitle='" + conferenceTitle + '\'' +
                '}';
    }
}
