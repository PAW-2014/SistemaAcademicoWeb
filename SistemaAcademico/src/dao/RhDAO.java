package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Coordenador;
import model.Diretor;
import model.Disciplina;
import model.Professor;
import model.Enum.SituProfessor;
import model.Enum.TipoProfessor;

public class RhDAO extends Conexao {

    static Connection conn = null;
    static PreparedStatement stmt = null;

    public static void main(String[] args) {
//        		RhDAO.importarDisciplinas();
        RhDAO.importarProfessores();
    }

    public static void importarDisciplinas() {
        List<Disciplina> disciplinas = null;

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO disciplina(nome) VALUES(?)");
        disciplinas = criarDisciplinas();

        for (Disciplina disciplina : disciplinas) {
            try {
                conn = Conexao.connectToDataBase();
                stmt = conn.prepareStatement(sql.toString());
                stmt.setString(1, disciplina.getNome());
                stmt.executeUpdate();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    connectToDataBase().rollback();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void importarProfessores() {
        List<Professor> professores = null;
        professores = criarProfessores();
        for (Professor professor : professores) {
            try {
                StringBuilder sql = new StringBuilder();
                sql.append("INSERT INTO professor(nome,login,senha,status,tipo) VALUES(?,?,?,?,?)");

                conn = Conexao.connectToDataBase();
                stmt = conn.prepareStatement(sql.toString());
                stmt.setString(1, professor.getNome());
                stmt.setString(2, professor.getLogin());
                stmt.setString(3, professor.getSenha());
                stmt.setInt(4, professor.getStatus().ordinal());
                stmt.setInt(5, professor.getTipo().ordinal());
                stmt.executeUpdate();
                if (professor.getClass().equals(Coordenador.class)) {
                    sql = new StringBuilder();
                    sql.append("INSERT INTO coordenador(idCoordenador) VALUES(?)");
                    stmt = conn.prepareStatement(sql.toString());
                    ProfessorDAO dao = new ProfessorDAO();
                    stmt.setInt(1, dao.getLastIdProfessor());
                    stmt.executeUpdate();
                } else if (professor.getClass().equals(Diretor.class)) {
                    sql = new StringBuilder();
                    sql.append("INSERT INTO diretor(idDiretor) VALUES(?)");
                    stmt = conn.prepareStatement(sql.toString());
                    ProfessorDAO dao = new ProfessorDAO();
                    stmt.setInt(1, dao.getLastIdProfessor());
                    stmt.executeUpdate();
                }
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    connectToDataBase().rollback();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static List<Disciplina> criarDisciplinas() {
        List<Disciplina> list = new ArrayList<Disciplina>();

        Disciplina d = new Disciplina();
        d.setNome("Introdução ao Cálculo");
        list.add(d);
        d = new Disciplina();
        d.setNome("Cálculo I");
        list.add(d);
        d = new Disciplina();
        d.setNome("Cálculo II");
        list.add(d);
        d = new Disciplina();
        d.setNome("Programação I");
        list.add(d);
        d = new Disciplina();
        d.setNome("Programação II");
        list.add(d);
        d = new Disciplina();
        d.setNome("Tópicos Avançados da Programação");
        list.add(d);
        d = new Disciplina();
        d.setNome("Algebra Linear");
        list.add(d);
        d = new Disciplina();
        d.setNome("Estrutura de Dados I");
        list.add(d);
        d = new Disciplina();
        d.setNome("Estrutura de Dados II");
        list.add(d);
        d = new Disciplina();
        d.setNome("Engenharia de Software I");
        list.add(d);
        d = new Disciplina();
        d.setNome("Engenharia de Software II");
        list.add(d);
        d = new Disciplina();
        d.setNome("Lógica");
        list.add(d);
        d = new Disciplina();
        d.setNome("Geometria Analática");
        list.add(d);
        d = new Disciplina();
        d.setNome("Programação II");
        list.add(d);
        d = new Disciplina();
        d.setNome("Arquitetura de Computadores I");
        list.add(d);
        d = new Disciplina();
        d.setNome("Arquitetura de Computadores II");
        list.add(d);
        d = new Disciplina();
        d.setNome("Introdução � Multimidia Web");
        list.add(d);
        d = new Disciplina();
        d.setNome("Banco de Dados I");
        list.add(d);
        d = new Disciplina();
        d.setNome("Banco de Dados II");
        list.add(d);
        d = new Disciplina();
        d.setNome("Introdução à Ciência da Computação");
        list.add(d);
        return list;
    }

    public static List<Professor> criarProfessores() {
        List<Professor> list = new ArrayList<Professor>();

        Professor p;
        p = new Professor();
        p.setTipo(TipoProfessor.Professor);
        p.setNome("Vinicius Rosalen");
        p.setStatus(SituProfessor.Ativo);
        p.setLogin("vinicius");
        p.setSenha("vinicius");
        list.add(p);

        p = new Professor();
        p.setTipo(TipoProfessor.Professor);
        p.setNome("Susilea Abreu");
        p.setStatus(SituProfessor.Ativo);
        p.setLogin("susilea");
        p.setSenha("susilea");
        list.add(p);

        p = new Professor();
        p.setTipo(TipoProfessor.Professor);
        p.setNome("Erlon Pinheiro");
        p.setStatus(SituProfessor.Ativo);
        p.setLogin("erlon");
        p.setSenha("erlon");
        list.add(p);

        p = new Professor();
        p.setTipo(TipoProfessor.Professor);
        p.setNome("Hudson Ramos");
        p.setStatus(SituProfessor.Ativo);
        p.setLogin("hudson");
        p.setSenha("hudson");
        list.add(p);

        p = new Professor();
        p.setTipo(TipoProfessor.Professor);
        p.setNome("Arnaldo");
        p.setStatus(SituProfessor.Ativo);
        p.setLogin("arnaldo");
        p.setSenha("arnaldo");
        list.add(p);

        p = new Professor();
        p.setTipo(TipoProfessor.Professor);
        p.setNome("Sandro Tonini");
        p.setStatus(SituProfessor.Ativo);
        p.setLogin("sandro");
        p.setSenha("sandro");
        list.add(p);

        Coordenador c = new Coordenador();
        c.setTipo(TipoProfessor.Coordenador);
        c.setNome("Cristiano Biancardi");
        c.setStatus(SituProfessor.Ativo);
        c.setLogin("cristiano");
        c.setSenha("cristiano");
        list.add(c);

        Diretor d = new Diretor();
        d.setTipo(TipoProfessor.Diretor);
        d.setNome("Diretor");
        d.setStatus(SituProfessor.Ativo);
        d.setLogin("diretor");
        d.setSenha("diretor");
        list.add(d);

        return list;
    }
}
