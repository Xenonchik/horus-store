package beholder.domain;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Blahblahblah
 */
@javax.persistence.Entity
@Table(name="cat_stores")
public class CatStore {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store", nullable = false)
    private Store store;

    @Column(name = "category", nullable = false)
    private Long category;

    @Id
    @Column(name = "url")
    private String url;


    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

}
