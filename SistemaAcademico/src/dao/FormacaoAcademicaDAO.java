package dao;

import dao.interfaces.IFormacaoAcademica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.AcademicFormation;
import model.Professor;

public class FormacaoAcademicaDAO extends MySQLDataBaseConnection implements IFormacaoAcademica{
    
    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    
    @Override
    public List<AcademicFormation> recoverAcademicFormations(Integer idProfessor){
        List<AcademicFormation> lista = new ArrayList<AcademicFormation>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM formacaoAcademica WHERE idProfessor = ?");
        try{
            conn = new MySQLDataBaseConnection().getConnection();
            stmt= conn.prepareStatement(sql.toString());
            stmt.setInt(1, idProfessor);
            rs = stmt.executeQuery();
            while(rs.next()){
                AcademicFormation form = new AcademicFormation();
                form.setIdFormacaoAcademica(rs.getInt("idFormacaoAcademica"));
                form.setDataFim(rs.getDate("dataFim"));
                form.setDataInicio(rs.getDate("dataInicio"));
                form.setInstituicao(rs.getString("instituicao"));
                form.setNomeCurso(rs.getString("nomeCurso"));
                form.setProfessor(new Professor());
                form.getProfessor().setId(rs.getInt("idProfessor"));
                lista.add(form);
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(Exception e){e.printStackTrace();}
        return lista;
    }
    @Override
   public void save(AcademicFormation f) {
        StringBuilder sql = new StringBuilder();
        try {
            conn = new MySQLDataBaseConnection().getConnection();
            if (f.getIdFormacaoAcademica() == null) {
                sql.append("INSERT INTO formacaoacademica(dataInicio,dataFim,nomeCurso,instituicao,idProfessor) ")
                        .append("VALUES(?,?,?,?,?)");
                stmt = conn.prepareStatement(sql.toString());
                stmt.setInt(5, f.getProfessor().getId());
            } else {
                sql.append("UPDATE formacaoacademica SET dataInicio=?,dataFim=?,nomeCurso=?,instituicao=? ")
                        .append("WHERE idFormacaoAcademica=?");
                stmt = conn.prepareStatement(sql.toString());
                stmt.setInt(5, f.getIdFormacaoAcademica());
            }
            stmt.setDate(1, new java.sql.Date(f.getDataInicio().getTime()));
            stmt.setDate(2, new java.sql.Date(f.getDataFim().getTime()));
            stmt.setString(3, f.getNomeCurso());
            stmt.setString(4, f.getInstituicao());
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public boolean verify(AcademicFormation form){
        Integer id = null;
         StringBuilder sql = new StringBuilder();
         sql.append("SELECT idFormacaoAcademica FROM formacaoacademica WHERE UPPER(nomeCurso) = UPPER(?) ")
         .append("AND UPPER(instituicao) = UPPER(?) AND dataInicio=? AND dataFim=? AND idProfessor = ? ");
         if(form.getIdFormacaoAcademica() != null)
             sql.append("AND idFormacaoAcademica <> ?");
        try{
            conn = new MySQLDataBaseConnection().getConnection();
            stmt = conn.prepareStatement(sql.toString());
            stmt.setString(1, form.getNomeCurso());
            stmt.setString(2, form.getInstituicao());
            stmt.setDate(3, new java.sql.Date(form.getDataInicio().getTime()));
            stmt.setDate(4, new java.sql.Date(form.getDataFim().getTime()));
            stmt.setInt(5, form.getProfessor().getId());
            if(form.getIdFormacaoAcademica() != null)
                 stmt.setInt(4, form.getIdFormacaoAcademica());
            rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("idFormacaoAcademica");
                break;
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(Exception e){e.printStackTrace();}
        
        return (id != null);
    }
    
    @Override
   public void delete(AcademicFormation f) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM formacaoAcademica WHERE idFormacaoAcademica =? ");
        try {
            conn = new MySQLDataBaseConnection().getConnection();
            stmt = conn.prepareStatement(sql.toString());
            stmt.setInt(1, f.getIdFormacaoAcademica());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
