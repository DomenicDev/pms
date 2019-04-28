package de.hfu.pms.shared.dto;

import java.util.Date;

public class ConsultingSupportDTO {

    private int id;
    private String typeOfConsulting;
    private Date consultingDate;
    private int duration; // in minutes

    public ConsultingSupportDTO(String typeOfConsulting, Date consultingDate, int duration) {
        this.typeOfConsulting = typeOfConsulting;
        this.consultingDate = consultingDate;
        this.duration = duration;
    }

    public ConsultingSupportDTO(int id, String typeOfConsulting, Date consultingDate, int duration) {
        this.id = id;
        this.typeOfConsulting = typeOfConsulting;
        this.consultingDate = consultingDate;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeOfConsulting() {
        return typeOfConsulting;
    }

    public void setTypeOfConsulting(String typeOfConsulting) {
        this.typeOfConsulting = typeOfConsulting;
    }

    public Date getConsultingDate() {
        return consultingDate;
    }

    public void setConsultingDate(Date consultingDate) {
        this.consultingDate = consultingDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
