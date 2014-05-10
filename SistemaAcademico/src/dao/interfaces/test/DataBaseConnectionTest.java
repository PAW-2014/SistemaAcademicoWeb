package dao.interfaces.test;

import static org.junit.Assert.*;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import dao.MySQLDataBaseConnection;

public class DataBaseConnectionTest {

	@Test
	public void testMySQLConnection() {
		Connection mySQLConnection = null;
		
		try {
			mySQLConnection = new MySQLDataBaseConnection().getConnection();
		} catch (Exception e) {
			fail("Error getting a connection to MySQL Server");
		}
		
		assertNotNull(mySQLConnection);
		new MySQLDataBaseConnection().closeConnection(mySQLConnection);
	}

	@Test
	public void testCloseConnectionGoodCase() {
		Connection mySQLConnection = null;
		
		try {
			mySQLConnection = new MySQLDataBaseConnection().getConnection();
		} catch (Exception e) {
			fail("Error getting a connection to MySQL Server");
		}
		
		new MySQLDataBaseConnection().closeConnection(mySQLConnection);
		
		try {
			assertTrue(mySQLConnection.isClosed());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test // Stupid case of Test
	public void testCloseConnectionNullConnection() {
		Connection mySQLConnection = null;
		
		new MySQLDataBaseConnection().closeConnection(mySQLConnection);
		
		assertNull(mySQLConnection);
	}
	
}
