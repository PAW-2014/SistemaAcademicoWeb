package dao.interfaces;

import java.sql.Connection;

public interface DataBaseConnection {
	
	public Connection connectToDataBase() throws Exception;	
}