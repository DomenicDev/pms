package de.hfu.pms.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table
public class TravelCostSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date date;

    @Column
    private String location;

    @Column
    private BigDecimal sum;

    private String title;

    public TravelCostSupport() {
    }

    /**
     * Use this constructor to describe travel costs to the partner university.
     * @param date
     * @param location
     * @param sum
     */
    public TravelCostSupport(Date date, String location, BigDecimal sum) {
        this.date = date;
        this.location = location;
        this.sum = sum;
    }

    /**
     * Use this constructor to describe travel costs of a conference.
     * @param date
     * @param location
     * @param sum
     * @param title
     */
    public TravelCostSupport(Date date, String location, BigDecimal sum, String title) {
        this.date = date;
        this.location = location;
        this.sum = sum;
        this.title = title;
    }
}
