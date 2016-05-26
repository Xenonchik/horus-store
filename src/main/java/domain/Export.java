package domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Blahblahblah
 */
@javax.persistence.Entity
@Table(name="export")
public class Export implements Entity {
    final static Logger log = LoggerFactory.getLogger(Export.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "description")
    private String description;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "price")
    private Long price;

    @Column(name = "url")
    private String url;

    @Column(name = "store")
    private String store;

    @Override
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setName(String name, List<String> brands) {
        HashSet<String> hs = new HashSet<>(brands);
        name = name.replace(Character.toString ((char) 160), " ");
        name = name.replace(" & ", "&");
        name = name.replace("Gunter Hauer", "Gunter&Hauer");
        name = name.replace("Le Chef", "Le_Chef");
        name = name.replace("LE CHEF", "Le_Chef");
        name = name.replace("IDEAL STANDARD", "IDEAL_STANDARD");
        for (String part : name.split("\\s")) {

            if(hs.contains(part.toUpperCase())) {
                String[] parts = name.split("\\s" + part + "\\s");
                if(parts.length == 2) {
                    this.setDescription(parts[0]);
                    this.setBrand(part);
                    this.setModel(parts[1]);
                    return;
                } else {
                    log.warn("Parsing product name: " + name);
                }
            }
        }
        log.warn("No brand for: " + name);
        this.setModel(name);
    }
}
