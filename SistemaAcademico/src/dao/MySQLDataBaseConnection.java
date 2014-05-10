package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dao.interfaces.DataBaseConnection;

public class MySQLDataBaseConnection implements DataBaseConnection {
	
	private static String host = "localhost";
	private static String port = "3306";
	private static String schemaName = "AcademicSistem";
	
	private static String baseUrl = "jdbc:mysql://{0}:{1}/{2}";
	
	private static String url = String.format(baseUrl, host, port, schemaName);
	private static String driverName = "com.mysql.jdbc.Driver";
	
	private static String usuario = "root";
	private static String senha = "toor";
	
	public Connection connectToDataBase() throws SQLException, ClassNotFoundException {
		
		Class.forName(driverName);
		return DriverManager.getConnection(url, usuario, senha);
		
	}	
}