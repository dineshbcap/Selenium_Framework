package utils.testdatareader; // FormattedDataSet API

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import utils.baseclass.TestBaseClass;

public abstract class DataAccessBase extends TestBaseClass implements DatabaseReader {

	
	protected Connection connection;
	protected Statement statement;
	protected ResultSet resultSet;
	private String dataSourceName;
	public EnvironmentPropertiesReader envParams;

	/**
	 * Set data source name
	 * 
	 * @param dataSourceName
	 */
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	/**
	 * Get data source name
	 * 
	 * @return dataSourceName
	 */
	public String getDataSourceName() {
		return dataSourceName;
	}

	/**
	 * Create a statement
	 * 
	 * @return createdStament
	 */
	public Statement createStatement() throws SQLException {
		return connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	}

	/**
	 * Execute a query
	 * 
	 * @param query
	 * @return result
	 */
	public ResultSet executeQuery(String query) throws SQLException {
		log.info("Executing query : " + query);
		connection = getConnection();
		statement = createStatement();
		return statement.executeQuery(query);
	}

	/**
	 * Close the statement
	 * 
	 */
	public void close() throws SQLException {
		closeResultSet();
		closeStatement();
	}

	/**
	 * Close the connection
	 * 
	 */
	public void closeConnection() throws SQLException {
		if (!connectionIsClosed()) {
			connection.close();
			connection = null;
		}
	}

	/**
	 * Verify connection closed
	 * 
	 * @return true/false
	 */
	public boolean connectionIsClosed() throws SQLException {
		return (connection == null || connection.isClosed());
	}

	/**
	 * Close the result set
	 * 
	 */
	public void closeResultSet() throws SQLException {

		if (resultSet != null) {
			resultSet.close();
			resultSet = null;
		}
	}

	/**
	 * Close the statement
	 * 
	 */
	public void closeStatement() throws SQLException {
		if (statement != null) {
			statement.close();
			statement = null;
		}
	}

	/**
	 * get the result set
	 * 
	 * @param queryCommand
	 * @return resultSet - the values in the result set
	 */
	public ResultSet getResultSet(String queryCommand) throws SQLException {
		log.info("Executing query : " + queryCommand);
		connection = getConnection();
		statement = createStatement();
		resultSet = executeQuery(queryCommand);
		closeStatement();
		closeConnection();
		return resultSet;
	}

	/**
	 * get the result set
	 * 
	 * @param queryCommand
	 * @return result - the values in the result set
	 */
	public String executeQueryReturnRandom(String queryCommand) throws SQLException {
		log.info("Executing query : " + queryCommand);
		Random rand = new Random();
		int min = 0, max = 0;
		String result = "";
		connection = getConnection();
		statement = createStatement();
		resultSet = executeQuery(queryCommand);
		if (resultSet.last()) {
			max = resultSet.getRow();
			int randomNum = rand.nextInt(max - min + 1) + min + 1;
			if (randomNum > max) {
				randomNum = max;
			}
			resultSet.absolute(randomNum);
			result = resultSet.getString(1);
			log.info("Value picked in Random : " + result);
			closeResultSet();
			closeStatement();
			closeConnection();
			return result;
		} else {
			throw new SQLException("ResultSet is empty");
		}
	}

	/**
	 * execute a query and get all the results
	 * 
	 * @param queryCommand
	 * @return result - all the values in the result set
	 */
	public ArrayList<String> executeQueryReturnAllResults(String queryCommand) throws SQLException {
		ArrayList<String> result = new ArrayList<String>();
		connection = getConnection();
		statement = createStatement();
		resultSet = executeQuery(queryCommand);
		while (resultSet.next()) {
			result.add(resultSet.getString(1));
		}
		closeResultSet();
		closeStatement();
		closeConnection();
		return result;
	}

	/**
	 * execute a query and return the row
	 * 
	 * @param queryCommand
	 * @param columnCount
	 * @param state
	 * @return dataValues - return a row
	 */
	public String[] executeQueryReturnRow(String queryCommand, int columnCount, String state) throws SQLException {
		String[] dataValues = new String[columnCount];
		boolean found = false;
		int counter = 0;
		connection = getConnection();
		statement = createStatement();
		try {
			do {
				Thread.sleep(10000);
				resultSet = executeQuery(queryCommand);
				counter++;
				found = resultSet.next();
				if (found) {
					if (!resultSet.getString("status1").equalsIgnoreCase(state)){
						found = false;
					}
				}
				if (counter == 10) {
					break;
				}

			} while (!found);
		} catch (Exception e) {
			e.getMessage();
		}

		resultSet.beforeFirst();
		while (resultSet.next()) {
			for (int j = 0; j < columnCount; j++) {
				if (j == 0){
					dataValues[j] = Integer.toString(resultSet.getInt(j + 1));
				}
				else{
					dataValues[j] = resultSet.getString(j + 1);
				}
			}
		}
		closeResultSet();
		closeStatement();
		closeConnection();
		return dataValues;
	}

	/**
	 * execute a query and get the value as 2D array
	 * 
	 * @param queryCommand
	 * @return dataValues - all the values in a 2D array
	 */
	public String[][] executeQueryReturnAs2dArray(String queryCommand) throws SQLException {
		String[][] dataValues = new String[1][2];
		int columnCount = 2;
		resultSet.beforeFirst();
		int k = 0;
		while (resultSet.next()) {
			for (int j = 1; j < columnCount; j++) {
				dataValues[k][j] = resultSet.getString(j);
			}
			k++;
		}
		closeResultSet();
		closeStatement();
		closeConnection();
		return dataValues;
	}

	/**
	 * execute a update and get the count
	 * 
	 * @param sql
	 * @return rowCount - count of the row
	 */
	public int executeUpdate(String sql) throws SQLException {
		log.info("Executing query : " + sql);
		connection = getConnection();
		statement = createStatement();
		int rowCount = statement.executeUpdate(sql);
		log.info("The update query affected : " + rowCount + " rows.");
		connection.commit();
		closeResultSet();
		closeStatement();
		closeConnection();
		return rowCount;
	}

	/**
	 * execute a query and get the first value
	 * 
	 * @param queryCommand
	 * @return firstVal - value of the first entry
	 */
	public String executeQueryReturnFirst(String queryCommand) throws SQLException {
		log.info("Executing query : " + queryCommand);
		connection = getConnection();
		statement = createStatement();
		resultSet = executeQuery(queryCommand);
		resultSet.first();
		String firstVal = resultSet.getString(1);
		log.info("The first value fetched : " + firstVal);
		closeResultSet();
		closeStatement();
		closeConnection();
		return firstVal;
	}

	/**
	 * execute a query and get the values up to given count
	 * 
	 * @param queryCommand
	 * @param countToReturn
	 * @return values - values up to the given count
	 */
	public String[] executeQueryReturnGivenCount(String queryCommand, int countToReturn) throws SQLException {
		log.info("Executing query : " + queryCommand);
		String[] values = new String[countToReturn];
		connection = getConnection();
		statement = createStatement();
		resultSet = executeQuery(queryCommand);

		int index = 0;

		while (resultSet.next() && index < countToReturn) {
			values[index++] = resultSet.getString(1);
		}
		closeResultSet();
		closeStatement();
		closeConnection();
		return values;
	}

	/**
	 * execute a query and get the values up to given count as Array
	 * 
	 * @param queryCommand
	 * @param countToReturn
	 * @return values - values up to the given count as array
	 */
	public ArrayList<String> executeQueryReturnGivenCountArray(String queryCommand, int countToReturn)
			throws SQLException {
		log.info("Executing query : " + queryCommand);
		ArrayList<String> values = new ArrayList<String>();
		connection = getConnection();
		statement = createStatement();
		resultSet = executeQuery(queryCommand);

		int index = 0;

		while (resultSet.next() && index < countToReturn) {
			values.add(resultSet.getString(1));
			index++;
		}
		closeResultSet();
		closeStatement();
		closeConnection();
		return values;
	}

	/**
	 * set Environment Parameters
	 * 
	 * @param params
	 */
	public void setEnvironmentParams(EnvironmentPropertiesReader params) {
		this.envParams = params;
	}

	/**
	 * Execute a query and return next
	 * 
	 * @param queryCommand
	 * @return next data of the result set
	 */
	public String executeQueryReturnNext(String queryCommand) throws SQLException {
		log.info("Executing query : " + queryCommand);
		connection = getConnection();
		statement = createStatement();
		resultSet = executeQuery(queryCommand);
		resultSet.next();
		closeStatement();
		closeConnection();
		return resultSet.getString(1);
	}
}
