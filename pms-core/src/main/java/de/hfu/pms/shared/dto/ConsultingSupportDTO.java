package de.hfu.pms.shared.dto;

import java.time.LocalDate;

public class ConsultingSupportDTO {

    private Long id;
    private String consultingType;
    private LocalDate consultingDate;
    private int consultingDuration; // in minutes

    public ConsultingSupportDTO() {

    }

    public ConsultingSupportDTO(String consultingType, LocalDate consultingDate, int consultingDuration) {
        this.consultingType = consultingType;
        this.consultingDate = consultingDate;
        this.consultingDuration = consultingDuration;
    }

    public ConsultingSupportDTO(Long id, String consultingType, LocalDate consultingDate, int consultingDuration) {
        this.id = id;
        this.consultingType = consultingType;
        this.consultingDate = consultingDate;
        this.consultingDuration = consultingDuration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsultingType() {
        return consultingType;
    }

    public void setConsultingType(String consultingType) {
        this.consultingType = consultingType;
    }

    public LocalDate getConsultingDate() {
        return consultingDate;
    }

    public void setConsultingDate(LocalDate consultingDate) {
        this.consultingDate = consultingDate;
    }

    public int getConsultingDuration() {
        return consultingDuration;
    }

    public void setConsultingDuration(int consultingDuration) {
        this.consultingDuration = consultingDuration;
    }

    @Override
    public String toString() {
        return "ConsultingSupportDTO{" +
                "id=" + id +
                ", consultingType='" + consultingType + '\'' +
                ", consultingDate=" + consultingDate +
                ", consultingDuration=" + consultingDuration +
                '}';
    }
}
