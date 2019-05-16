package de.hfu.pms.shared.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TravelCostUniversityDTO {

    private Long id;
    private LocalDate date;
    private BigDecimal sum;

    public TravelCostUniversityDTO() {
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

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "TravelCostUniversityDTO{" +
                "id=" + id +
                ", date=" + date +
                ", sum=" + sum +
                '}';
    }
}
