package model;

import java.io.Serializable;

public class PreferentialDiscipline implements Serializable {

    private static final long serialVersionUID = -4446636618419395435L;
    
    private Integer id;
    private Professor professor;
    private Discipline discipline;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public String toString() {
      return discipline.getName();
    }

    @Override
    public int hashCode() {//TODO HashCode
        int hash = 5;
        hash = 19 * hash + (this.discipline != null ? this.discipline.hashCode() : 0);
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
        final PreferentialDiscipline other = (PreferentialDiscipline) obj;
        return this.discipline.equals(other.getDiscipline());
    }
}
