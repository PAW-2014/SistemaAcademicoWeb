package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name="address")
public class Address implements Serializable {

	private static final long serialVersionUID = 1214404202282130103L;

	@Id
	@Column(name="id", nullable=false)
	private Integer id;
	
	@Column(name="street", nullable=false)
	private String street;
	
	@Column(name="number", nullable=false)
	private Integer number;
	
	@Column(name="neighborhood")
	private String neighborhood;
	
	@Column(name="city")
	private String city;
	
	@Column(name="federativeUnit")
	private String federativeUnit;
	
	@Column(name="zipCode", nullable=false)
	private String zipCode;
	
	@OneToOne(mappedBy="address", fetch=FetchType.EAGER)
	private Professor professor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFederativeUnit() {
		return federativeUnit;
	}

	public void setFederativeUnit(String federativeUnit) {
		this.federativeUnit = federativeUnit;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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
