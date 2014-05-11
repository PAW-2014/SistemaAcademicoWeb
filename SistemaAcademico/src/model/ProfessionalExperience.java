package model;

import java.io.Serializable;
import java.util.Date;

public class ProfessionalExperience implements Serializable {

    private static final long serialVersionUID = 4936612376428359672L;
    
    private Integer id;
    private String firm;
    private Date startDate;
    private Date endDate;
    private String function;
    
    private Professor professor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

	//TODO Equals
	//TODO HashCode
}
