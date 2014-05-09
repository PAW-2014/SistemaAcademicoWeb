package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Disciplina;
import dao.interfaces.IDisciplina;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DisciplinaPreferencial;
import model.Professor;

public class DisciplinaDAO extends Conexao implements IDisciplina {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    @Override
    public List<Disciplina> listarDisciplinas() {
        Disciplina d = null;
        List<Disciplina> disciplinas = new ArrayList<Disciplina>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM disciplina ORDER BY nome ASC");
        try {
            conn = Conexao.connectToDataBase();
            stmt = conn.prepareStatement(sql.toString());
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

    @Override
    public List<DisciplinaPreferencial> getDisiciplinasPreferenciais(Integer idProfessor) {
        List<DisciplinaPreferencial> lista = new ArrayList<DisciplinaPreferencial>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM disciplinaPreferencial dp INNER JOIN disciplina d WHERE dp.idProfessor = ? ")
                .append("AND dp.idDisciplina = d.idDisciplina");
        try {
            conn = Conexao.connectToDataBase();
            stmt = conn.prepareStatement(sql.toString());
            stmt.setInt(1, idProfessor);
            rs = stmt.executeQuery();
            while (rs.next()) {
                DisciplinaPreferencial dp = new DisciplinaPreferencial();
                dp.setId(rs.getInt("dp.idDisciplinaPreferencial"));
                dp.setProfessor(new Professor());
                dp.getProfessor().setId(idProfessor);
                Disciplina d = new Disciplina();
                d.setIdDisciplina(rs.getInt("dp.idDisciplina"));
                d.setNome(rs.getString("d.nome"));
                dp.setDisciplina(d);
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
    public void salvarDisciplinasPreferenciais(List<DisciplinaPreferencial> disciplinas, List<DisciplinaPreferencial> exclu) {
        StringBuilder sql = new StringBuilder();
        try {
            sql.append("INSERT INTO disciplinapreferencial(idDisciplina,idProfessor) ")
                    .append("VALUES(?,?)");
            conn = Conexao.connectToDataBase();
            conn.setAutoCommit(false);
            for (DisciplinaPreferencial d : disciplinas) {
                if(d.getId() != null){
                    if(!verificarDisciplinaPreferencial(d))
                        continue;
                }
                    stmt = conn.prepareStatement(sql.toString());
                    stmt.setInt(1, d.getDisciplina().getIdDisciplina());
                    stmt.setInt(2, d.getProfessor().getId());
                    stmt.executeUpdate();
                
            }
            sql = new StringBuilder();
            sql.append("DELETE FROM disciplinapreferencial WHERE idDisciplina =? AND idProfessor=?");
            for (DisciplinaPreferencial d : exclu) {
                if(d.getId() != null){
                    stmt = conn.prepareStatement(sql.toString());
                    stmt.setInt(1, d.getDisciplina().getIdDisciplina());
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

    private boolean verificarDisciplinaPreferencial(DisciplinaPreferencial d) {
        Integer id = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT idDisciplinaPreferencial FROM disciplinapreferencial WHERE idDisciplina = ? AND idProfessor=?");
        try {
            stmt = conn.prepareStatement(sql.toString());
            stmt.setInt(1, d.getDisciplina().getIdDisciplina());
            stmt.setInt(2, d.getProfessor().getId());
            stmt.executeQuery();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (id != null);
    }
}
