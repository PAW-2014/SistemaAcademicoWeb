package dao;

import dao.interfaces.IExperienciaProfissional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.ProfessionalExperience;
import model.Professor;

public class ExperienciaProfissionalDAO extends MySQLDataBaseConnection implements IExperienciaProfissional {
private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    
    @Override
    public List<ProfessionalExperience> recoverProfessionalExperiences(Integer idProfessor){
        List<ProfessionalExperience> lista = new ArrayList<ProfessionalExperience>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM experienciaprofissional WHERE idProfessor = ?");
        try{
            conn = new MySQLDataBaseConnection().getConnection();
            stmt= conn.prepareStatement(sql.toString());
            stmt.setInt(1, idProfessor);
            rs = stmt.executeQuery();
            while(rs.next()){
                ProfessionalExperience exp = new ProfessionalExperience();
                exp.setId(rs.getInt("idExperienciaProfissional"));
                exp.setEndDate(rs.getDate("dataFim"));
                exp.setStartDate(rs.getDate("dataInicio"));
                exp.setFirm(rs.getString("empresa"));
                exp.setFunction(rs.getString("funcao"));
                exp.setProfessor(new Professor());
                exp.getProfessor().setId(rs.getInt("idProfessor"));
                lista.add(exp);
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(Exception e){e.printStackTrace();}
        return lista;
    }
    @Override
   public void save(ProfessionalExperience ep) {
        StringBuilder sql = new StringBuilder();
        try {
            conn = new MySQLDataBaseConnection().getConnection();
            if (ep.getId() == null) {
                sql.append("INSERT INTO experienciaprofissional(dataInicio,dataFim,empresa,funcao,idProfessor) ")
                        .append("VALUES(?,?,?,?,?)");
                stmt = conn.prepareStatement(sql.toString());
                stmt.setInt(5, ep.getProfessor().getId());
            } else {
                sql.append("UPDATE experienciaprofissional SET dataInicio=?,dataFim=?,empresa=?,funcao=? ")
                        .append("WHERE idExperienciaProfissional=?");
                stmt = conn.prepareStatement(sql.toString());
                stmt.setInt(5, ep.getId());
            }
            stmt.setDate(1, new java.sql.Date(ep.getStartDate().getTime()));
            stmt.setDate(2, new java.sql.Date(ep.getEndDate().getTime()));
            stmt.setString(3, ep.getFirm());
            stmt.setString(4, ep.getFunction());
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
     @Override
    public boolean verify(ProfessionalExperience exp){
        Integer id = null;
         StringBuilder sql = new StringBuilder();
         sql.append("SELECT idExperienciaProfissional FROM experienciaprofissional WHERE UPPER(empresa) = UPPER(?) ")
         .append("AND UPPER(funcao) = UPPER(?) AND dataInicio=? AND dataFim=? AND idProfessor = ? ");
         if(exp.getId() != null)
             sql.append("AND idExperienciaProfissional <> ?");
        try{
            conn = new MySQLDataBaseConnection().getConnection();
            stmt = conn.prepareStatement(sql.toString());
            stmt.setString(1, exp.getFirm());
            stmt.setString(2, exp.getFunction());
            stmt.setDate(3, new java.sql.Date(exp.getStartDate().getTime()));
            stmt.setDate(4, new java.sql.Date(exp.getEndDate().getTime()));
            stmt.setInt(5, exp.getProfessor().getId());
            if(exp.getId() != null)
                 stmt.setInt(6, exp.getId());
            rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("idExperienciaProfissional");
                break;
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(Exception e){e.printStackTrace();}
        
        return (id != null);
    }
    
    @Override
   public void delete(ProfessionalExperience ep) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM experienciaprofissional WHERE idExperienciaProfissional =? ");
        try {
            conn = new MySQLDataBaseConnection().getConnection();
            stmt = conn.prepareStatement(sql.toString());
            stmt.setInt(1, ep.getId());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
