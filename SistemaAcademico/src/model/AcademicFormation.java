package model;

import java.io.Serializable;
import java.util.Date;

public class AcademicFormation implements Serializable {
	
    private static final long serialVersionUID = 2793663142281056249L;
    private Integer idFormacaoAcademica;
    private Date dataInicio;
    private Date dataFim;
    private String nomeCurso;
    private String instituicao;
    private Professor professor;

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Integer getIdFormacaoAcademica() {
        return idFormacaoAcademica;
    }

    public void setIdFormacaoAcademica(Integer idFormacaoAcademica) {
        this.idFormacaoAcademica = idFormacaoAcademica;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.idFormacaoAcademica != null ? this.idFormacaoAcademica.hashCode() : 0);
        hash = 67 * hash + (this.dataInicio != null ? this.dataInicio.hashCode() : 0);
        hash = 67 * hash + (this.dataFim != null ? this.dataFim.hashCode() : 0);
        hash = 67 * hash + (this.nomeCurso != null ? this.nomeCurso.hashCode() : 0);
        hash = 67 * hash + (this.instituicao != null ? this.instituicao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) { // TODO Adjust the Equals Function
    	
        if (obj == null)
            return false;
        
        if (getClass() != obj.getClass())
            return false;
        
        return true;
    }

}
