package dao;

import dao.interfaces.IProfessor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Address;
import model.Enum.SituProfessor;
import model.Enum.TipoProfessor;
import model.Professor;

public class ProfessorDAO extends MySQLDataBaseConnection implements IProfessor {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    @Override
    public Professor validateLogin(String login, String senha) {
        Integer id = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT idProfessor FROM professor ")
                .append("WHERE login=? AND senha=? AND status = 0");
        try {
            conn = new MySQLDataBaseConnection().getConnection();
            stmt = conn.prepareStatement(sql.toString());
            stmt.setString(1, login);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("idProfessor");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (id != null) ? recoverProfessor(id) : null;
    }

    @Override
    public Professor recoverProfessor(Integer id) {
        Professor p = null;
        Integer idEnd = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM professor ")
                .append("WHERE idProfessor=?");
        try {
            conn = new MySQLDataBaseConnection().getConnection();
            stmt = conn.prepareStatement(sql.toString());
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                p = new Professor();
                p.setId(rs.getInt("idProfessor"));
                p.setName(rs.getString("nome"));
                p.setLogin(rs.getString("login"));
                p.setPassword(rs.getString("senha"));
                p.setPhoneNumber(rs.getString("telefone"));
                p.setEmail(rs.getString("email"));
                p.setPhoto(rs.getBytes("foto"));
                p.setCpf(rs.getString("cpf"));
                p.setBirthDate(rs.getDate("dataNascimento"));
                p.setGeneralRegisterNumber(rs.getString("rg"));
                p.setOtherInformations(rs.getString("outrasInformacoes"));
                p.setStatus(SituProfessor.indice(rs.getInt("status")));
                p.setType(TipoProfessor.indice(rs.getInt("tipo")));
                idEnd = rs.getInt("idEndereco");
            }
            rs.close();
            stmt.close();
            conn.close();
            p.setAddress(recuperarEndereco(idEnd));
            if (p.getAddress() == null) {
                p.setAddress(new Address());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    private Address recuperarEndereco(Integer id) {
        Address e = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM endereco ")
                .append("WHERE idEndereco=?");
        try {
            conn = new MySQLDataBaseConnection().getConnection();
            stmt = conn.prepareStatement(sql.toString());
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                e = new Address();
                e.setId(rs.getInt("idEndereco"));
                e.setNumber((Integer) rs.getObject("numero"));
                e.setNeighborhood(rs.getString("bairro"));
                e.setZipCode(rs.getString("cep"));
                e.setCity(rs.getString("cidade"));
                e.setStreet(rs.getString("logradouro"));
                e.setFederativeUnit(rs.getString("uf"));
            }
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return e;
    }

    @Override
    public void updateData(Professor p) {
        StringBuilder sql = new StringBuilder();

        try {
            conn = new MySQLDataBaseConnection().getConnection();
            conn.setAutoCommit(false);
            salvarOuAtualizarEndereco(p.getAddress());
            sql.append("UPDATE professor SET nome=?,senha=?,login=?,telefone=?,email=?, ")
                    .append("foto=?,cpf=?,dataNascimento=?,rg=?,outrasInformacoes=?,status=? ");
            if (p.getAddress() != null && p.getAddress().getId() != null) {
                sql.append(",idEndereco = ? ");
            }
            sql.append("WHERE idProfessor=? ");
            stmt = conn.prepareStatement(sql.toString());
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getPassword());
            stmt.setString(3, p.getLogin());
            stmt.setString(4, p.getPhoneNumber());
            stmt.setString(5, p.getEmail());
            stmt.setBytes(6, p.getPhoto());
            stmt.setString(7, p.getCpf());
            stmt.setObject(8, (p.getBirthDate() != null) ? new java.sql.Date(p.getBirthDate().getTime()) : null);
            stmt.setString(9, p.getGeneralRegisterNumber());
            stmt.setString(10, p.getOtherInformations());
            stmt.setObject(11, p.getStatus().ordinal());
            if (p.getAddress() != null && p.getAddress().getId() != null) {
                stmt.setInt(12, p.getAddress().getId());
                stmt.setInt(13, p.getId());
            } else {
                stmt.setInt(12, p.getId());
            }
            stmt.executeUpdate();

            stmt.close();
            conn.commit();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (!conn.isClosed()) {
                    conn.rollback();
                }
                System.out.println("rollback: salvar professor");// TODO Coment�rio
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    private Integer getLastIdEndereco() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT MAX(idendereco) as last FROM endereco");
        Integer id = null;
        try {
            stmt = conn.prepareStatement(sql.toString());
            rs = stmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("last");
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public Integer getLastIdProfessor() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT MAX(idprofessor) as last FROM professor");
        Integer id = null;
        try {
            conn = new MySQLDataBaseConnection().getConnection();
            stmt = conn.prepareStatement(sql.toString());
            rs = stmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("last");
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    private void salvarOuAtualizarEndereco(Address e) {
        StringBuilder sql = new StringBuilder();
        try {
            if (e.getId() == null) {
                sql.append("INSERT INTO endereco(logradouro,numero,bairro,cidade,uf,cep) ")
                        .append("VALUES(?,?,?,?,?,?)");
                stmt = conn.prepareStatement(sql.toString());
            } else {
                sql.append("UPDATE endereco SET logradouro=?,numero=?,bairro=?,cidade=?,uf=?,cep=? ")
                        .append("WHERE idEndereco=?");
                stmt = conn.prepareStatement(sql.toString());
                stmt.setInt(7, e.getId());
            }
            stmt.setString(1, e.getStreet());
            stmt.setObject(2, e.getNumber());
            stmt.setString(3, e.getNeighborhood());
            stmt.setString(4, e.getCity());
            stmt.setString(5, e.getFederativeUnit());
            stmt.setString(6, e.getZipCode());
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (e.getId() == null) {
            e.setId(getLastIdEndereco());
        }
    }

    @Override
    public List<Professor> listProfessors() {
        Professor p = new Professor();
        List<Professor> professores = new ArrayList<Professor>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.IdProfessor, p.nome, p.status FROM professor p WHERE p.tipo = ? ORDER BY p.nome ASC");
        try {
            conn = new MySQLDataBaseConnection().getConnection();
            stmt = conn.prepareStatement(sql.toString());
            stmt.setInt(1, TipoProfessor.Professor.ordinal());
            rs = stmt.executeQuery();

            while (rs.next()) {
                p = new Professor();
                p.setName(rs.getString("nome"));
                p.setId(rs.getInt("idProfessor"));
                p.setStatus(SituProfessor.indice(rs.getInt("status")));
                professores.add(p);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professores;
    }

    @Override
    public List<Professor> listAll() {
        Professor p = new Professor();
        List<Professor> professores = new ArrayList<Professor>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.idProfessor, p.nome FROM professor p ORDER BY p.nome ASC");
        try {
            conn = new MySQLDataBaseConnection().getConnection();
            stmt = conn.prepareStatement(sql.toString());
            rs = stmt.executeQuery();

            while (rs.next()) {
                p = new Professor();
                p.setName(rs.getString("nome"));
                p.setId(rs.getInt("idProfessor"));
                professores.add(p);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professores;
    }
}
