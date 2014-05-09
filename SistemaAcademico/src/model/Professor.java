package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Enum.SituProfessor;
import model.Enum.TipoProfessor;

public class Professor implements Serializable, Comparable<Professor> {

    private static final long serialVersionUID = 4466417793771253444L;
    
    private Integer id;
    private String nome;
    private String senha;
    private String login;
    private String telefone;
    private String email;
    private byte[] foto;
    private String cpf;
    private Date dataNascimento;
    private String rg;
    private String outrasInformacoes;
    private SituProfessor status;
    private TipoProfessor tipo;
    private Endereco endereco;
    private List<ProfessionalExperience> experienciasProfissionais;
    private List<AcademicFormation> formacoesAcademicas;
    private List<DisciplinaPreferencial> disciplinasPreferenciais;

    public Professor() {
        this.experienciasProfissionais = new ArrayList<ProfessionalExperience>();
        this.formacoesAcademicas = new ArrayList<AcademicFormation>();
        this.endereco = new Endereco();
        this.disciplinasPreferenciais = new ArrayList<DisciplinaPreferencial>();
    }
    
    public boolean addDisciplinaPreferencial(DisciplinaPreferencial disciplina) {
        boolean result;
        try {
            if (disciplina != null && !disciplinasPreferenciais.contains(disciplina)) {
                result = disciplinasPreferenciais.add(disciplina);
                disciplina.setProfessor(this);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeDisciplinaPreferencial(DisciplinaPreferencial disciplina) {
        boolean result;
        try {
            if (disciplina != null && disciplinasPreferenciais.contains(disciplina)) {
                result = disciplinasPreferenciais.remove(disciplina);
                disciplina.setProfessor(null);
                disciplina.setDisciplina(null);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        return nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getOutrasInformacoes() {
        return outrasInformacoes;
    }

    public void setOutrasInformacoes(String outrasInformacoes) {
        this.outrasInformacoes = outrasInformacoes;
    }

    public SituProfessor getStatus() {
        return status;
    }

    public void setStatus(SituProfessor status) {
        this.status = status;
    }

    public TipoProfessor getTipo() {
        return tipo;
    }

    public void setTipo(TipoProfessor tipo) {
        this.tipo = tipo;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<ProfessionalExperience> getExperienciasProfissionais() {
        return experienciasProfissionais;
    }

    public void setExperienciasProfissionais(
            List<ProfessionalExperience> experienciasProfissionais) {
        this.experienciasProfissionais = experienciasProfissionais;
    }

    public List<AcademicFormation> getFormacoesAcademicas() {
        return formacoesAcademicas;
    }

    public void setFormacoesAcademicas(List<AcademicFormation> formacoesAcademicas) {
        this.formacoesAcademicas = formacoesAcademicas;
    }

    public List<DisciplinaPreferencial> getDisciplinasPreferenciais() {
        return disciplinasPreferenciais;
    }

    public void setDisciplinasPreferenciais(
            List<DisciplinaPreferencial> disciplinasPreferenciais) {
        this.disciplinasPreferenciais = disciplinasPreferenciais;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Professor other = (Professor) obj;
        return this.id == other.getId();
    }
    

    @Override
    public int compareTo(Professor o) {
        return this.id.compareTo(o.getId());
    }
}
