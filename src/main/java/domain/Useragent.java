package domain;

import javax.persistence.*;

/**
 * Blahblahblah
 */
@javax.persistence.Entity
@Table(name="useragents")
public class Useragent {

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
