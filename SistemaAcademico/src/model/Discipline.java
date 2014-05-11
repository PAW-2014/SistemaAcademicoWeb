package model;

import java.io.Serializable;

public class Discipline implements Comparable<Discipline>, Serializable {

    private static final long serialVersionUID = -6913931188543025636L;
    
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Discipline o) {
        return this.name.compareTo(o.getName());
    }

    @Override
    public int hashCode() {//TODO HashCode
        int hash = 3;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {//TODO Equals
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Discipline other = (Discipline) obj;
        return this.id == other.getId();
    }
}
