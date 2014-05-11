package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="professionalExperience")
public class ProfessionalExperience implements Serializable {

    private static final long serialVersionUID = 4936612376428359672L;
    
    @Id
    @Column(name="id", nullable=false)
    private Integer id;
    
    @Column(name="firm", nullable=false)
    private String firm;

    @Column(name="startDate", nullable=false)
    private Date startDate;

    @Column(name="endDate", nullable=false)
    private Date endDate;

    @Column(name="function", nullable=false)
    private String function;
    
    @JoinColumn(name="professorId", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
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
