package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.print.attribute.standard.Destination;

/**
 * Blahblahblah
 */
@javax.persistence.Entity
@Table(name="emir_goods")
public class OldGood {

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category")
    private Category category;

    @Transient
    private Integer storeCount = 0;

    @OneToMany(mappedBy = "good", fetch = FetchType.EAGER)
    private Set<Alias> storedAliases;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Map<String, Export> getAliases() {
        return aliases;
    }

    public void setAliases(Map<String, Export> aliases) {
        this.aliases = aliases;
    }


    public void incStoreCount() {
        storeCount = getStoreCount() + 1;
    }

    public Integer getStoreCount() {
        return storeCount;
    }

    public Set<Alias> getStoredAliases() {
        return storedAliases;
    }

    public void setStoredAliases(Set<Alias> storedAliases) {
        this.storedAliases = storedAliases;
    }

}
