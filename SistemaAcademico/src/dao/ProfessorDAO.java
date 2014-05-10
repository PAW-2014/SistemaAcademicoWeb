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
                p.setNome(rs.getString("nome"));
                p.setLogin(rs.getString("login"));
                p.setSenha(rs.getString("senha"));
                p.setTelefone(rs.getString("telefone"));
                p.setEmail(rs.getString("email"));
                p.setFoto(rs.getBytes("foto"));
                p.setCpf(rs.getString("cpf"));
                p.setDataNascimento(rs.getDate("dataNascimento"));
                p.setRg(rs.getString("rg"));
                p.setOutrasInformacoes(rs.getString("outrasInformacoes"));
                p.setStatus(SituProfessor.indice(rs.getInt("status")));
                p.setTipo(TipoProfessor.indice(rs.getInt("tipo")));
                idEnd = rs.getInt("idEndereco");
            }
            rs.close();
            stmt.close();
            conn.close();
            p.setEndereco(recuperarEndereco(idEnd));
            if (p.getEndereco() == null) {
                p.setEndereco(new Address());
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
                e.setIdEndereco(rs.getInt("idEndereco"));
                e.setNumero((Integer) rs.getObject("numero"));
                e.setBairro(rs.getString("bairro"));
                e.setCep(rs.getString("cep"));
                e.setCidade(rs.getString("cidade"));
                e.setLogradouro(rs.getString("logradouro"));
                e.setUf(rs.getString("uf"));
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
            salvarOuAtualizarEndereco(p.getEndereco());
            sql.append("UPDATE professor SET nome=?,senha=?,login=?,telefone=?,email=?, ")
                    .append("foto=?,cpf=?,dataNascimento=?,rg=?,outrasInformacoes=?,status=? ");
            if (p.getEndereco() != null && p.getEndereco().getIdEndereco() != null) {
                sql.append(",idEndereco = ? ");
            }
            sql.append("WHERE idProfessor=? ");
            stmt = conn.prepareStatement(sql.toString());
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getSenha());
            stmt.setString(3, p.getLogin());
            stmt.setString(4, p.getTelefone());
            stmt.setString(5, p.getEmail());
            stmt.setBytes(6, p.getFoto());
            stmt.setString(7, p.getCpf());
            stmt.setObject(8, (p.getDataNascimento() != null) ? new java.sql.Date(p.getDataNascimento().getTime()) : null);
            stmt.setString(9, p.getRg());
            stmt.setString(10, p.getOutrasInformacoes());
            stmt.setObject(11, p.getStatus().ordinal());
            if (p.getEndereco() != null && p.getEndereco().getIdEndereco() != null) {
                stmt.setInt(12, p.getEndereco().getIdEndereco());
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
            if (e.getIdEndereco() == null) {
                sql.append("INSERT INTO endereco(logradouro,numero,bairro,cidade,uf,cep) ")
                        .append("VALUES(?,?,?,?,?,?)");
                stmt = conn.prepareStatement(sql.toString());
            } else {
                sql.append("UPDATE endereco SET logradouro=?,numero=?,bairro=?,cidade=?,uf=?,cep=? ")
                        .append("WHERE idEndereco=?");
                stmt = conn.prepareStatement(sql.toString());
                stmt.setInt(7, e.getIdEndereco());
            }
            stmt.setString(1, e.getLogradouro());
            stmt.setObject(2, e.getNumero());
            stmt.setString(3, e.getBairro());
            stmt.setString(4, e.getCidade());
            stmt.setString(5, e.getUf());
            stmt.setString(6, e.getCep());
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (e.getIdEndereco() == null) {
            e.setIdEndereco(getLastIdEndereco());
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
                p.setNome(rs.getString("nome"));
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
                p.setNome(rs.getString("nome"));
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
