package dao;

import dao.interfaces.IDiretor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Enum.TipoProfessor;
import model.Professor;

public class DiretorDAO extends Conexao implements IDiretor {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    @Override
    public void promoverProfessor(Integer idProfessor) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE professor p SET p.tipo = ? WHERE p.IdProfessor = ?");
        try {
            conn = Conexao.pegaCon();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(sql.toString());
            stmt.setInt(1, TipoProfessor.Coordenador.ordinal());
            stmt.setInt(2, idProfessor);
            stmt.executeUpdate();

            criarCoordenador(idProfessor);
            conn.commit();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(DiretorDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            e.printStackTrace();
        }
    }

    private void criarCoordenador(Integer idProfessor) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO coordenador(idCoordenador) VALUES(?)");
        try {
            stmt = conn.prepareStatement(sql.toString());
            stmt.setInt(1, idProfessor);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excluirProfessor(Professor p) {
        StringBuilder sql = new StringBuilder();
        try {
            conn = Conexao.pegaCon();
            conn.setAutoCommit(false);
            if (p.getEndereco() != null && p.getEndereco().getIdEndereco() != null) {
                excluirEndereco(p.getEndereco().getIdEndereco());
            }
            excluirFormacoes(p.getId());
            excluirDisciplinasPreferenciais(p.getId());
            excluirExperiencias(p.getId());
            sql.append("DELETE FROM professor WHERE IdProfessor =?");
            stmt = conn.prepareStatement(sql.toString());
            stmt.setInt(1, p.getId());
            stmt.executeUpdate();
            conn.commit();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(DiretorDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            e.printStackTrace();
        }
    }

    private void excluirEndereco(Integer idEndereco) {
        StringBuilder sql = new StringBuilder();
        try {
            sql.append("DELETE FROM endereco WHERE IdEndereco = ?");
            stmt = conn.prepareStatement(sql.toString());
            stmt.setInt(1, idEndereco);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void excluirFormacoes(Integer id) {
        StringBuilder sql = new StringBuilder();
        try {
            sql.append("DELETE FROM formacaoAcademica WHERE idProfessor = ?");
            stmt = conn.prepareStatement(sql.toString());
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void excluirDisciplinasPreferenciais(Integer id) {
       StringBuilder sql = new StringBuilder();
        try {
            sql.append("DELETE FROM disciplinapreferencial WHERE idProfessor = ?");
            stmt = conn.prepareStatement(sql.toString());
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void excluirExperiencias(Integer id) {
       StringBuilder sql = new StringBuilder();
        try {
            sql.append("DELETE FROM experienciaprofissional WHERE idProfessor = ?");
            stmt = conn.prepareStatement(sql.toString());
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
