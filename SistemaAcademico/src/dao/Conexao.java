package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	static String url = "jdbc:mysql://localhost:3306/sistema academico";
	static String usuario = "root";
	static String senha = "root";
	
	public static Connection pegaCon() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url, usuario, senha);
		return conn;
	}	
}
