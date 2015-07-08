package utils.testdatareader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DataAccessClient extends DataAccessBase {
	private String dbDriver;
	private String dbUrl;
	private String userName;
	private String passWord;

	
	public DataAccessClient(String dbDriver, String dbUrl, String userName, String password) throws SQLException {
		try {
			this.dbDriver = dbDriver;
			this.dbUrl = dbUrl;
			this.userName = userName;
			this.passWord = password;
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			throw new SQLException("Database Access Client Failed with message=" + e.getMessage());
		}
	}

	/** Factory method that allows this class to clone itself */
	public DataAccessBase createInstance() throws SQLException {
		return new DataAccessClient(dbDriver, dbUrl, userName, passWord);
	}

	/** Get database connection */

	public Connection getConnection() throws SQLException {
		if (connectionIsClosed()) {
			connection = DriverManager.getConnection(dbUrl, userName, passWord);
		}
		return connection;
	}

	/** Close open Statements, ResultSets and the Connection */
	public void close() throws SQLException {
		try {
			closeResultSet();
			closeStatement();
		} finally {
			closeConnection();
		}
	}
}
