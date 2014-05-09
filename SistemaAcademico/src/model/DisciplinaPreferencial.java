package model;

import java.io.Serializable;

public class DisciplinaPreferencial implements Serializable {

    private static final long serialVersionUID = -4446636618419395435L;
    private Integer id;
    private Professor professor;
    private Disciplina disciplina;

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String toString() {
      return disciplina.getNome();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + (this.disciplina != null ? this.disciplina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DisciplinaPreferencial other = (DisciplinaPreferencial) obj;
        return this.disciplina.equals(other.getDisciplina());
    }
}
