package de.hfu.pms.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TravelCostUniversityDTO {

    private Long id;
    private Date date;
    private BigDecimal sum;

    public TravelCostUniversityDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
