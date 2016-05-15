package main.model;

/**
 * Created by Polina on 15/05/2016.
 */

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Table(name = "Stock", uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class Stock {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "ELEMENTS_FOR_COUPE", unique = false)
    private Integer availableElementsForCoupe;

    @Column(name = "ELEMENTS_FOR_VAN", unique = false)
    private Integer availableElementsForVan;

    @Column(name = "ELEMENTS_FOR_CABRIO", unique = false)
    private Integer availableElementsForCabrio;

    @Column(name = "TIMESTAMP", unique = false, nullable = false, length = 100)
    private Timestamp timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp orderDate) {
        this.timestamp = orderDate;
    }

    public Integer getAvailableElementsForCabrio() {
        return availableElementsForCabrio;
    }

    public void setAvailableElementsForCabrio(Integer availableElementsForCabrio) {
        this.availableElementsForCabrio = availableElementsForCabrio;
    }

    public Integer getAvailableElementsForVan() {
        return availableElementsForVan;
    }

    public void setAvailableElementsForVan(Integer availableElementsForVan) {
        this.availableElementsForVan = availableElementsForVan;
    }

    public Integer getAvailableElementsForCoupe() {
        return availableElementsForCoupe;
    }

    public void setAvailableElementsForCoupe(Integer availableElementsForCoupe) {
        this.availableElementsForCoupe = availableElementsForCoupe;
    }
}
