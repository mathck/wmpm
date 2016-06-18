package main.model;

import javax.persistence.*;

@Entity
@Table(name = "Stock", uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "NAME", unique = false)
    private String stockName;

    @Column(name = "AVAILABLE_COUNT", unique = false)
    private Integer avaliableCount;


    public Integer getId() {
        return id;
    }

    public String getStockName() {
        return stockName;
    }

    public Integer getAvaliableCount() {
        return avaliableCount;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public void setAvaliableCount(Integer avaliableCount) {
        this.avaliableCount = avaliableCount;
    }

    @Override
    public String toString() {
        return "ELEMENTS{" +
                "id=" + id +
                ", STOCK_NAME='" + stockName + '\'' +
                ", AVAILABLE_COUNT='" + avaliableCount + '\'' +
                '}';
    }
}
