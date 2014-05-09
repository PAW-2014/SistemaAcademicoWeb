package model;

import java.io.Serializable;
import java.util.Date;

public class ProfessionalExperience implements Serializable {

    private static final long serialVersionUID = 4936612376428359672L;
    private Integer idExperiencia;
    private String empresa;
    private Date dataInicio;
    private Date dataFim;
    private String funcao;
    private Professor professor;

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

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

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Integer getIdExperiencia() {
        return idExperiencia;
    }

    public void setIdExperiencia(Integer idExperiencia) {
        this.idExperiencia = idExperiencia;
    }
}
