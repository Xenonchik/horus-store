package domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Blahblahblah
 */
@javax.persistence.Entity
@Table(name="emir_goods")
public class EmirGood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "t1")
    private String t1;

    @Column(name = "t2")
    private String t2;

    @Column(name = "t3")
    private String t3;

    @Column(name = "t4")
    private String t4;

    @Column(name = "model")
    private String model;

    @Column(name = "category")
    private Integer category;

    /**
     * Store name - alias
     */
    @Transient
    private Map<String, Export> aliases = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public String getT2() {
        return t2;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }

    public String getT3() {
        return t3;
    }

    public void setT3(String t3) {
        this.t3 = t3;
    }

    public String getT4() {
        return t4;
    }

    public void setT4(String t4) {
        this.t4 = t4;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Map<String, Export> getAliases() {
        return aliases;
    }

    public void setAliases(Map<String, Export> aliases) {
        this.aliases = aliases;
    }
}
