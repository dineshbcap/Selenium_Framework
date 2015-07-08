package utils.testdatareader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract class for reading data from Database
 */
public interface DatabaseReader {

	public DataAccessBase createInstance() throws SQLException;

	public void setDataSourceName(String dataSourceName);

	public String getDataSourceName();

	public Connection getConnection() throws SQLException;

	public void close() throws SQLException;

	public void closeConnection() throws SQLException;

	public void closeResultSet() throws SQLException;

	public void closeStatement() throws SQLException;

	public boolean connectionIsClosed() throws SQLException;

	public ResultSet getResultSet(String queryCommand) throws SQLException;

	public int executeUpdate(String updateCommand) throws SQLException;
}
