package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;

import dao.interfaces.IDataBaseConnection;

public class MySQLDataBaseConnection implements IDataBaseConnection {
	
	private static String host = "localhost";
	private static String port = "3306";
	private static String schemaName = "AcademicSystem";
	
	private static String baseUrl = "jdbc:mysql://{0}:{1}/{2}";
	
	private static String url = MessageFormat.format(baseUrl, host, port, schemaName);
	private static String driverName = "com.mysql.jdbc.Driver";
	
	private static String usuario = "root";
	private static String senha = "toor";
	
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		
		Class.forName(driverName);
		return DriverManager.getConnection(url, usuario, senha);
		
	}

	@Override
	public void closeConnection(Connection connection) {
		try{
			if(connection == null || connection.isClosed())
				return;
			
			connection.close();
		}catch (Exception exception) {
		}
	}
		
}