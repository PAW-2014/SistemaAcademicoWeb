package model;

import java.io.Serializable;

public class Disciplina implements Comparable<Disciplina>, Serializable {

    private static final long serialVersionUID = -6913931188543025636L;
    private Integer idDisciplina;
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toString() {
        return nome;
    }

    @Override
    public int compareTo(Disciplina o) {
        return this.nome.compareTo(o.getNome());
    }

    public Integer getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Integer idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.idDisciplina != null ? this.idDisciplina.hashCode() : 0);
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
        final Disciplina other = (Disciplina) obj;
        return this.idDisciplina == other.getIdDisciplina();
    }
}
