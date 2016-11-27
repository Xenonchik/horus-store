package beholder.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Blahblahblah
 */
@javax.persistence.Entity
@Table(name="brands")
public class Brand {

    @Id
    @Column(name = "name")
    private String name;

    private String parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getParent() {
        return (parent != null && parent.length() > 0) ? parent : name;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
