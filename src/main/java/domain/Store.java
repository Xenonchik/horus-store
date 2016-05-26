package domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Blahblahblah
 */
@javax.persistence.Entity
@Table(name="stores")
public class Store {

    @Id
    @Column(name = "id",unique=true, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Integer status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "store", cascade = CascadeType.ALL)
    private Set<Category> categories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
