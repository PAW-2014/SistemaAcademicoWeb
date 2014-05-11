//package dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.util.ArrayList;
//import java.util.List;
//
//import model.Coordinator;
//import model.Principal;
//import model.Discipline;
//import model.Professor;
//import model.Enum.SituProfessor;
//import model.Enum.TypeProfessor;
//
//public class RhDAO {
//
//    static Connection conn = null;
//    static PreparedStatement stmt = null;
//
//    public static void main(String[] args) {
////        		RhDAO.importarDisciplinas();
//        RhDAO.importarProfessores();
//    }
//
//    public static void importarDisciplinas() {
//        List<Discipline> disciplinas = null;
//
//        StringBuilder sql = new StringBuilder();
//        sql.append("INSERT INTO disciplina(nome) VALUES(?)");
//        disciplinas = criarDisciplinas();
//
//        for (Discipline disciplina : disciplinas) {
//            try {
//                conn = new MySQLDataBaseConnection().getConnection();
//                stmt = conn.prepareStatement(sql.toString());
//                stmt.setString(1, disciplina.getName());
//                stmt.executeUpdate();
//                stmt.close();
//                conn.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//                try {
//                    conn.rollback();
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public static void importarProfessores() {
//        List<Professor> professores = null;
//        professores = criarProfessores();
//        for (Professor professor : professores) {
//            try {
//                StringBuilder sql = new StringBuilder();
//                sql.append("INSERT INTO professor(nome,login,senha,status,tipo) VALUES(?,?,?,?,?)");
//
//                conn = new MySQLDataBaseConnection().getConnection();
//                stmt = conn.prepareStatement(sql.toString());
//                stmt.setString(1, professor.getName());
//                stmt.setString(2, professor.getLogin());
//                stmt.setString(3, professor.getPassword());
//                stmt.setInt(4, professor.getStatus().ordinal());
//                stmt.setInt(5, professor.getType().ordinal());
//                stmt.executeUpdate();
//                if (professor.getClass().equals(Coordinator.class)) {
//                    sql = new StringBuilder();
//                    sql.append("INSERT INTO coordenador(idCoordenador) VALUES(?)");
//                    stmt = conn.prepareStatement(sql.toString());
//                    ProfessorDAO dao = new ProfessorDAO();
//                    stmt.setInt(1, dao.getLastIdProfessor());
//                    stmt.executeUpdate();
//                } else if (professor.getClass().equals(Principal.class)) {
//                    sql = new StringBuilder();
//                    sql.append("INSERT INTO diretor(idDiretor) VALUES(?)");
//                    stmt = conn.prepareStatement(sql.toString());
//                    ProfessorDAO dao = new ProfessorDAO();
//                    stmt.setInt(1, dao.getLastIdProfessor());
//                    stmt.executeUpdate();
//                }
//                stmt.close();
//                conn.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//                try {
//                    conn.rollback();
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public static List<Discipline> criarDisciplinas() {
//        List<Discipline> list = new ArrayList<Discipline>();
//
//        Discipline d = new Discipline();
//        d.setName("Introdução ao Cálculo");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Cálculo I");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Cálculo II");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Programação I");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Programação II");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Tópicos Avançados da Programação");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Algebra Linear");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Estrutura de Dados I");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Estrutura de Dados II");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Engenharia de Software I");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Engenharia de Software II");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Lógica");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Geometria Analática");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Programação II");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Arquitetura de Computadores I");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Arquitetura de Computadores II");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Introdução � Multimidia Web");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Banco de Dados I");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Banco de Dados II");
//        list.add(d);
//        d = new Discipline();
//        d.setName("Introdução à Ciência da Computação");
//        list.add(d);
//        return list;
//    }
//
//    public static List<Professor> criarProfessores() {
//        List<Professor> list = new ArrayList<Professor>();
//
//        Professor p;
//        p = new Professor();
//        p.setType(TypeProfessor.Professor);
//        p.setName("Vinicius Rosalen");
//        p.setStatus(SituProfessor.Ativo);
//        p.setLogin("vinicius");
//        p.setPassword("vinicius");
//        list.add(p);
//
//        p = new Professor();
//        p.setType(TypeProfessor.Professor);
//        p.setName("Susilea Abreu");
//        p.setStatus(SituProfessor.Ativo);
//        p.setLogin("susilea");
//        p.setPassword("susilea");
//        list.add(p);
//
//        p = new Professor();
//        p.setType(TypeProfessor.Professor);
//        p.setName("Erlon Pinheiro");
//        p.setStatus(SituProfessor.Ativo);
//        p.setLogin("erlon");
//        p.setPassword("erlon");
//        list.add(p);
//
//        p = new Professor();
//        p.setType(TypeProfessor.Professor);
//        p.setName("Hudson Ramos");
//        p.setStatus(SituProfessor.Ativo);
//        p.setLogin("hudson");
//        p.setPassword("hudson");
//        list.add(p);
//
//        p = new Professor();
//        p.setType(TypeProfessor.Professor);
//        p.setName("Arnaldo");
//        p.setStatus(SituProfessor.Ativo);
//        p.setLogin("arnaldo");
//        p.setPassword("arnaldo");
//        list.add(p);
//
//        p = new Professor();
//        p.setType(TypeProfessor.Professor);
//        p.setName("Sandro Tonini");
//        p.setStatus(SituProfessor.Ativo);
//        p.setLogin("sandro");
//        p.setPassword("sandro");
//        list.add(p);
//
//        Coordinator c = new Coordinator();
//        c.setType(TypeProfessor.Coordenador);
//        c.setName("Cristiano Biancardi");
//        c.setStatus(SituProfessor.Ativo);
//        c.setLogin("cristiano");
//        c.setPassword("cristiano");
//        list.add(c);
//
//        Principal d = new Principal();
//        d.setType(TypeProfessor.Diretor);
//        d.setName("Diretor");
//        d.setStatus(SituProfessor.Ativo);
//        d.setLogin("diretor");
//        d.setPassword("diretor");
//        list.add(d);
//
//        return list;
//    }
//}
