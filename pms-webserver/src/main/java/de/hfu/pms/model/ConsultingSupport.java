package de.hfu.pms.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class ConsultingSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate consultingDate;

    @Column
    private String consultingType;

    @Column
    private int consultingDuration; // in minutes

    public ConsultingSupport() {
    }

    public ConsultingSupport(LocalDate consultingDate, String consultingType, int consultingDuration) {
        this.consultingDate = consultingDate;
        this.consultingType = consultingType;
        this.consultingDuration = consultingDuration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getConsultingDate() {
        return consultingDate;
    }

    public void setConsultingDate(LocalDate consultingDate) {
        this.consultingDate = consultingDate;
    }

    public String getConsultingType() {
        return consultingType;
    }

    public void setConsultingType(String consultingType) {
        this.consultingType = consultingType;
    }

    public int getConsultingDuration() {
        return consultingDuration;
    }

    public void setConsultingDuration(int consultingDuration) {
        this.consultingDuration = consultingDuration;
    }
}
