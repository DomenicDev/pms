package de.hfu.pms.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table
public class TravelCostUniversity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date date;

    @Column
    private BigDecimal sum;

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
