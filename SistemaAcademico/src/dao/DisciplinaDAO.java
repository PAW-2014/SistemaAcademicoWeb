package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Discipline;
import dao.interfaces.IDisciplina;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.PreferentialDiscipline;
import model.Professor;

public class DisciplinaDAO extends MySQLDataBaseConnection implements IDisciplina {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    @Override
    public List<Discipline> listarDisciplinas() {
        Discipline d = null;
        List<Discipline> disciplinas = new ArrayList<Discipline>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM disciplina ORDER BY nome ASC");
        try {
            conn = new MySQLDataBaseConnection().getConnection();
            stmt = conn.prepareStatement(sql.toString());
            rs = stmt.executeQuery();

            while (rs.next()) {
                d = new Discipline();
                d.setName(rs.getString("nome"));
                d.setId(rs.getInt("idDisciplina"));
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

    @Override
    public List<PreferentialDiscipline> getDisiciplinasPreferenciais(Integer idProfessor) {
        List<PreferentialDiscipline> lista = new ArrayList<PreferentialDiscipline>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM disciplinaPreferencial dp INNER JOIN disciplina d WHERE dp.idProfessor = ? ")
                .append("AND dp.idDisciplina = d.idDisciplina");
        try {
            conn = new MySQLDataBaseConnection().getConnection();
            stmt = conn.prepareStatement(sql.toString());
            stmt.setInt(1, idProfessor);
            rs = stmt.executeQuery();
            while (rs.next()) {
                PreferentialDiscipline dp = new PreferentialDiscipline();
                dp.setId(rs.getInt("dp.idDisciplinaPreferencial"));
                dp.setProfessor(new Professor());
                dp.getProfessor().setId(idProfessor);
                Discipline d = new Discipline();
                d.setId(rs.getInt("dp.idDisciplina"));
                d.setName(rs.getString("d.nome"));
                dp.setDiscipline(d);
                lista.add(dp);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void salvarDisciplinasPreferenciais(List<PreferentialDiscipline> disciplinas, List<PreferentialDiscipline> exclu) {
        StringBuilder sql = new StringBuilder();
        try {
            sql.append("INSERT INTO disciplinapreferencial(idDisciplina,idProfessor) ")
                    .append("VALUES(?,?)");
            conn = new MySQLDataBaseConnection().getConnection();
            conn.setAutoCommit(false);
            for (PreferentialDiscipline d : disciplinas) {
                if(d.getId() != null){
                    if(!verificarDisciplinaPreferencial(d))
                        continue;
                }
                    stmt = conn.prepareStatement(sql.toString());
                    stmt.setInt(1, d.getDiscipline().getId());
                    stmt.setInt(2, d.getProfessor().getId());
                    stmt.executeUpdate();
                
            }
            sql = new StringBuilder();
            sql.append("DELETE FROM disciplinapreferencial WHERE idDisciplina =? AND idProfessor=?");
            for (PreferentialDiscipline d : exclu) {
                if(d.getId() != null){
                    stmt = conn.prepareStatement(sql.toString());
                    stmt.setInt(1, d.getDiscipline().getId());
                    stmt.setInt(2, d.getProfessor().getId());
                    stmt.executeUpdate();
                }
            }
            stmt.close();
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ex.printStackTrace();
        }
    }

    private boolean verificarDisciplinaPreferencial(PreferentialDiscipline d) {
        Integer id = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT idDisciplinaPreferencial FROM disciplinapreferencial WHERE idDisciplina = ? AND idProfessor=?");
        try {
            stmt = conn.prepareStatement(sql.toString());
            stmt.setInt(1, d.getDiscipline().getId());
            stmt.setInt(2, d.getProfessor().getId());
            stmt.executeQuery();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (id != null);
    }
}
