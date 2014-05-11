package dao.interfaces;

import java.sql.Connection;

public interface IDataBaseConnection {
	
	public Connection getConnection() throws Exception;
	
	public void closeConnection(Connection connection);
}