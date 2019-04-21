package de.hfu.pms.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class ConsultingSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Date consultingDate;

    @Column
    private String consultingType;

    @Column
    private int consultingDuration; // in minutes

    public ConsultingSupport(Date consultingDate, String consultingType, int consultingDuration) {
        this.consultingDate = consultingDate;
        this.consultingType = consultingType;
        this.consultingDuration = consultingDuration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getConsultingDate() {
        return consultingDate;
    }

    public void setConsultingDate(Date consultingDate) {
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
