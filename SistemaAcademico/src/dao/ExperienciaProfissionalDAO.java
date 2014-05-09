package dao;

import dao.interfaces.IExperienciaProfissional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.ExperienciaProfissional;
import model.Professor;

public class ExperienciaProfissionalDAO extends Conexao implements IExperienciaProfissional {
private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    
    @Override
    public List<ExperienciaProfissional> getExperienciasProfissionais(Integer idProfessor){
        List<ExperienciaProfissional> lista = new ArrayList<ExperienciaProfissional>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM experienciaprofissional WHERE idProfessor = ?");
        try{
            conn = Conexao.pegaCon();
            stmt= conn.prepareStatement(sql.toString());
            stmt.setInt(1, idProfessor);
            rs = stmt.executeQuery();
            while(rs.next()){
                ExperienciaProfissional exp = new ExperienciaProfissional();
                exp.setIdExperiencia(rs.getInt("idExperienciaProfissional"));
                exp.setDataFim(rs.getDate("dataFim"));
                exp.setDataInicio(rs.getDate("dataInicio"));
                exp.setEmpresa(rs.getString("empresa"));
                exp.setFuncao(rs.getString("funcao"));
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
   public void salvarOuAtualizarExperiencia(ExperienciaProfissional ep) {
        StringBuilder sql = new StringBuilder();
        try {
            conn = Conexao.pegaCon();
            if (ep.getIdExperiencia() == null) {
                sql.append("INSERT INTO experienciaprofissional(dataInicio,dataFim,empresa,funcao,idProfessor) ")
                        .append("VALUES(?,?,?,?,?)");
                stmt = conn.prepareStatement(sql.toString());
                stmt.setInt(5, ep.getProfessor().getId());
            } else {
                sql.append("UPDATE experienciaprofissional SET dataInicio=?,dataFim=?,empresa=?,funcao=? ")
                        .append("WHERE idExperienciaProfissional=?");
                stmt = conn.prepareStatement(sql.toString());
                stmt.setInt(5, ep.getIdExperiencia());
            }
            stmt.setDate(1, new java.sql.Date(ep.getDataInicio().getTime()));
            stmt.setDate(2, new java.sql.Date(ep.getDataFim().getTime()));
            stmt.setString(3, ep.getEmpresa());
            stmt.setString(4, ep.getFuncao());
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
     @Override
    public boolean verificarExperiencia(ExperienciaProfissional exp){
        Integer id = null;
         StringBuilder sql = new StringBuilder();
         sql.append("SELECT idExperienciaProfissional FROM experienciaprofissional WHERE UPPER(empresa) = UPPER(?) ")
         .append("AND UPPER(funcao) = UPPER(?) AND dataInicio=? AND dataFim=? AND idProfessor = ? ");
         if(exp.getIdExperiencia() != null)
             sql.append("AND idExperienciaProfissional <> ?");
        try{
            conn = Conexao.pegaCon();
            stmt = conn.prepareStatement(sql.toString());
            stmt.setString(1, exp.getEmpresa());
            stmt.setString(2, exp.getFuncao());
            stmt.setDate(3, new java.sql.Date(exp.getDataInicio().getTime()));
            stmt.setDate(4, new java.sql.Date(exp.getDataFim().getTime()));
            stmt.setInt(5, exp.getProfessor().getId());
            if(exp.getIdExperiencia() != null)
                 stmt.setInt(6, exp.getIdExperiencia());
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
   public void excluirExperiencia(ExperienciaProfissional ep) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM experienciaprofissional WHERE idExperienciaProfissional =? ");
        try {
            conn = Conexao.pegaCon();
            stmt = conn.prepareStatement(sql.toString());
            stmt.setInt(1, ep.getIdExperiencia());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
