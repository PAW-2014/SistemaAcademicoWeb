package dao.interfaces;

import java.sql.Connection;

public interface DataBaseConnection {
	
	public Connection getConnection() throws Exception;
	
	public void closeConnection(Connection connection);
}