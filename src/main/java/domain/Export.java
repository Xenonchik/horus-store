package domain;

import java.util.Date;
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
@Table(name = "export")
public class Export {
  final static Logger log = LoggerFactory.getLogger(Export.class);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "date")
  private Date date;

  @Column(name = "description")
  private String description = "";

  @Column(name = "brand")
  private String brand = "";

  @Column(name = "model")
  private String model = "";

  @Column(name = "price")
  private Long price;

  @Column(name = "url")
  private String url;

  @Column(name = "store")
  private String store;

  @Column(name = "old_name")
  private String oldName;

  @Column(name = "category")
  private Long category;

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

  public void setName(String name, List<Brand> brands) {
    name = sanitizeName(name);
    for (Brand brand : brands) {
      if (name.contains(" " + brand.getName() + " ") || name.startsWith(brand.getName() + " ")) {
        String[] parts = name.split("\\s" + brand.getName() + "\\s");
        if (parts.length == 2) {
          this.setDescription(parts[0]);
          this.setBrand(brand.getParent());
          this.setModel(parts[1]);
          return;
        } else if (name.startsWith(brand.getParent())) {
          this.setBrand(brand.getParent());
          this.setModel(name.replace(brand + " ", ""));
          this.setDescription("");
          return;
        } else {
          log.warn("Parsing product name: " + name);
        }
      }
    }

    log.warn("No brand for: " + name + " url: " + this.getUrl());
    this.setModel(name);
    this.setDescription("");
    this.setBrand("");
  }

  private String sanitizeName(String name) {
    name = name.replace(Character.toString((char) 160), " ");
    return name.toUpperCase();
  }

  public Long getCategory() {
    return category;
  }

  public void setCategory(Long category) {
    this.category = category;
  }

  public String getFullName() {
    return description + " " + brand + " " + model;
  }

  public String getOldName() {
    return oldName;
  }

  public void setOldName(String oldName) {
    this.oldName = oldName;
  }
}
