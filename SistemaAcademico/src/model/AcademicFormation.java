package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="academicFormation")
public class AcademicFormation implements Serializable {
	
    private static final long serialVersionUID = 2793663142281056249L;
    
    @Id
    @Column(name="id", nullable=false)
    private Integer id;
    
    @Column(name="startDate", nullable=false)
    private Date startDate;
    
    @Column(name="endDate", nullable=false)
    private Date endDate;
    
    @Column(name="courseName", nullable=false)
    private String courseName;
    
    @Column(name="institute", nullable=false)
    private String institute;
    
    @JoinColumn(name="professorId", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private Professor professor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @Override
    public int hashCode() { // TODO Adjust hash Code
        int hash = 5;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 67 * hash + (this.startDate != null ? this.startDate.hashCode() : 0);
        hash = 67 * hash + (this.endDate != null ? this.endDate.hashCode() : 0);
        hash = 67 * hash + (this.courseName != null ? this.courseName.hashCode() : 0);
        hash = 67 * hash + (this.institute != null ? this.institute.hashCode() : 0);
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
