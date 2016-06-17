package main.model;

import main.model.enums.ElementsName;

import javax.persistence.*;

@Entity
@Table(name = "Stock", uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "ELEMENTS_NAME", unique = false)
    private ElementsName elementsName;

    @Column(name = "AVAILABLE_COUNT", unique = false)
    private Integer avaliableCount;


    public Integer getId() {
        return id;
    }

    public ElementsName getElementsName() {
        return elementsName;
    }

    public Integer getAvaliableCount() {
        return avaliableCount;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setElementsName(ElementsName elementsName) {
        this.elementsName = elementsName;
    }

    public void setAvaliableCount(Integer avaliableCount) {
        this.avaliableCount = avaliableCount;
    }


    @Override
    public String toString() {
        return "ELEMENTS{" +
                "id=" + id +
                ", ELEMENTS_NAME='" + elementsName + '\'' +
                ", AVAILABLE_COUNT='" + avaliableCount + '\'' +
                '}';
    }
//    @Column(name = "ELEMENTS_FOR_COUPE", unique = false)
//    private Integer availableElementsForCoupe;
//
//    @Column(name = "ELEMENTS_FOR_VAN", unique = false)
//    private Integer availableElementsForVan;
//
//    @Column(name = "ELEMENTS_FOR_CABRIO", unique = false)
//    private Integer availableElementsForCabrio;
//
//    @Column(name = "TIMESTAMP", unique = false, nullable = false, length = 100)
//    private Timestamp timestamp;
//
//    public Integer getId() {
//        return id;
//    }
//

//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Timestamp getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(Timestamp orderDate) {
//        this.timestamp = orderDate;
//    }
//
//    public Integer getAvailableElementsForCabrio() {
//        return availableElementsForCabrio;
//    }
//
//    public void setAvailableElementsForCabrio(Integer availableElementsForCabrio) {
//        this.availableElementsForCabrio = availableElementsForCabrio;
//    }
//
//    public Integer getAvailableElementsForVan() {
//        return availableElementsForVan;
//    }
//
//    public void setAvailableElementsForVan(Integer availableElementsForVan) {
//        this.availableElementsForVan = availableElementsForVan;
//    }
//
//    public Integer getAvailableElementsForCoupe() {
//        return availableElementsForCoupe;
//    }
//
//    public void setAvailableElementsForCoupe(Integer availableElementsForCoupe) {
//        this.availableElementsForCoupe = availableElementsForCoupe;
//    }
}
