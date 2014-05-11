package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import model.Enum.SituProfessor;
import model.Enum.TypeProfessor;

@Entity(name="professor")
@Inheritance(strategy=InheritanceType.JOINED)
public class Professor implements Serializable, Comparable<Professor> {

	private static final long serialVersionUID = 4466417793771253444L;

	@Id
	@Column(name="id", nullable=false)
	private Integer id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="password", nullable=false)
	private String password;

	@Column(name="login", nullable=false)
	private String login;
	
	@Column(name="phoneNumber", nullable=false)
	private String phoneNumber;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@Column(name="cpf", nullable=false)
	private String cpf;
	
	@Column(name="birthDate", nullable=false)
	private Date birthDate;
	
	@Column(name="generalRegisterNumber", nullable=false)
	private String generalRegisterNumber;
	
	@Column(name="status", nullable=false)
	private SituProfessor status;
	
	@Column(name="type", nullable=false)
	private TypeProfessor type;
	
	@Column(name="otherInformations")
	private String otherInformations;
	
	@Lob
	@Column(name="photo")
	private byte[] photo;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="addressId", nullable=false)
	private Address address;
	
	@OneToMany(mappedBy="professor", fetch=FetchType.LAZY)
	private List<ProfessionalExperience> professionalExperiences;
	
	@OneToMany(mappedBy="professor", fetch=FetchType.LAZY)
	private List<AcademicFormation> academicFormations;
	
	@OneToMany(mappedBy="professor", fetch=FetchType.LAZY)
	private List<PreferentialDiscipline> preferentialDisciplines;

	public Professor() {
		this.professionalExperiences = new ArrayList<ProfessionalExperience>();
		this.academicFormations = new ArrayList<AcademicFormation>();
		this.address = new Address();
		this.preferentialDisciplines = new ArrayList<PreferentialDiscipline>();
	}

	public boolean addDisciplinaPreferencial(PreferentialDiscipline disciplina) {
		boolean result;
		try {
			if (disciplina != null
					&& !preferentialDisciplines.contains(disciplina)) {
				result = preferentialDisciplines.add(disciplina);
				disciplina.setProfessor(this);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean removeDisciplinaPreferencial(
			PreferentialDiscipline disciplina) {
		boolean result;
		try {
			if (disciplina != null
					&& preferentialDisciplines.contains(disciplina)) {
				result = preferentialDisciplines.remove(disciplina);
				disciplina.setProfessor(null);
				disciplina.setDiscipline(null);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGeneralRegisterNumber() {
		return generalRegisterNumber;
	}

	public void setGeneralRegisterNumber(String generalRegisterNumber) {
		this.generalRegisterNumber = generalRegisterNumber;
	}

	public String getOtherInformations() {
		return otherInformations;
	}

	public void setOtherInformations(String otherInformations) {
		this.otherInformations = otherInformations;
	}

	public SituProfessor getStatus() {
		return status;
	}

	public void setStatus(SituProfessor status) {
		this.status = status;
	}

	public TypeProfessor getType() {
		return type;
	}

	public void setType(TypeProfessor type) {
		this.type = type;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<ProfessionalExperience> getProfessionalExperiences() {
		return professionalExperiences;
	}

	public void setProfessionalExperiences(List<ProfessionalExperience> professionalExperiences) {
		this.professionalExperiences = professionalExperiences;
	}

	public List<AcademicFormation> getAcademicFormations() {
		return academicFormations;
	}

	public void setAcademicFormations(
			List<AcademicFormation> academicFormations) {
		this.academicFormations = academicFormations;
	}

	public List<PreferentialDiscipline> getPreferentialDisciplines() {
		return preferentialDisciplines;
	}

	public void setPreferentialDisciplines(
			List<PreferentialDiscipline> preferentialDisciplines) {
		this.preferentialDisciplines = preferentialDisciplines;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() { // TODO hashCode
		int hash = 7;
		hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) { // TODO equals
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Professor other = (Professor) obj;
		return this.id == other.getId();
	}

	@Override
	public int compareTo(Professor o) { // TODO compareTo
		try {
			return this.id.compareTo(o.getId());
		} catch (Exception exception) {
			return 1;
		}
	}
}
