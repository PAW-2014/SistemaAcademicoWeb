package dao;

import dao.interfaces.ICoordenador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Disciplina;
import model.Enum.SituProfessor;
import model.Professor;

public class CoordenadorDAO extends MySQLDataBaseConnection implements ICoordenador {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    @Override
    public List<Professor> filtrarProfessor(String nome) {
        List<Professor> retorno = new ArrayList<Professor>();
        Professor p;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM professor WHERE nome LIKE ? ORDER BY nome ASC");
        try {
            conn = new MySQLDataBaseConnection().connectToDataBase();
            stmt = conn.prepareStatement(sql.toString());
            stmt.setString(1, "%" + nome);
            rs = stmt.executeQuery();

            while (rs.next()) {
                p = new Professor();
                p.setId(rs.getInt("idProfessor"));
                p.setNome(rs.getString("nome"));
                p.setTelefone(rs.getString("telefone"));
                p.setEmail(rs.getString("email"));
                p.setCpf(rs.getString("cpf"));
                p.setStatus(SituProfessor.indice(rs.getInt("status")));
                retorno.add(p);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    @Override
    public List<Disciplina> filtrarDisciplina(String nome) {
        Disciplina d = null;
        List<Disciplina> disciplinas = new ArrayList<Disciplina>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM disciplina WHERE nome LIKE ? ORDER BY nome ASC");
        try {
            conn = new MySQLDataBaseConnection().connectToDataBase();
            stmt = conn.prepareStatement(sql.toString());
            stmt.setString(1, "%"+nome);
            rs = stmt.executeQuery();

            while (rs.next()) {
                d = new Disciplina();
                d.setNome(rs.getString("nome"));
                d.setIdDisciplina(rs.getInt("idDisciplina"));
                disciplinas.add(d);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return disciplinas;
    }
}
