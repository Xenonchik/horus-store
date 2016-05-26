package domain;

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
}
