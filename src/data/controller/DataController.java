package data.controller;

/*
 * @Author Walker Hunter 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import data.model.QueryInfo;
import data.view.DataFrame;

public class DataController
{
	private String connectionString;
	private Connection dataConnection;
	private DataAppController baseController;
	private String currentQuery;
	long startTime, endTime;

	/**
	 * Declares objects and loads object
	 * @param baseController 
	 * @param baseController 
	 */
	public DataController(DataAppController baseController)
	{
		this.connectionString = "jdbc:mysql://127.0.0.1/games?user=root";//gets
																			//the
																			//database
																			//address		
		this.baseController = baseController;
		checkDriver();
		setupConnection();
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));
	}
	
	/**
	 * Change connection string so we are able to talk to other databases
	 * 
	 * @param pathToDBServer
	 *            path to the database server
	 * @param databaseName
	 *            name of the database
	 * @param userName
	 *            user name for the database server
	 * @param password
	 *            password for the database server
	 */
	public void connectionStringBuilder(String pathToDBServer, String databaseName, String userName, String password)
	{
		connectionString = "jdbc:mysql://";// wipes out connection string,
											// starts new
		connectionString += pathToDBServer;
		connectionString += "/" + databaseName;
		connectionString += "?user=" + userName;// "?" = end of path, sends it
												// to program at end of path
		connectionString += "&password=" + password;
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));
	}
	
	public void submitQuery(String tableName)
	{

	}
	
	/**
	 * Looks for the jdbc
	 * Looks for errors and exits
	 */
	private void checkDriver()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception currentException)
		{
			displayErrors(currentException);
			System.exit(1);
		}
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));
	}
	
	/*
	 * closes connection
	 * displays any errors that occur
	 */
	
	public void closeConnection()
	{
		try
		{
			dataConnection.close();
		}
		catch (SQLException currentException)
		{
			displayErrors(currentException);
		}
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));
	}
	
	/**
	 * setups the connection
	 * displays any errors that occur
	 */
	private void setupConnection()
	{
		try
		{
			dataConnection = DriverManager.getConnection(connectionString);
		}
		catch(SQLException currentException)
		{
			displayErrors(currentException);
		}
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));
	}

	
	/**
	 * displays errors
	 * @param currentException
	 */
	public void displayErrors(Exception currentException)
	{
		JOptionPane.showMessageDialog(baseController.getAppFrame(), currentException.getMessage());
		if(currentException instanceof SQLException)
		{
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL State: " + ((SQLException) currentException).getSQLState());
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL Error Code: " + ((SQLException) currentException).getErrorCode());
		}
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));
	}

	/*
	 * gets the appframe for the data base display
	 */
	
	public String displayTables()
	{
		String results = "";
		String query = "SHOW TABLES";
		
		try
		{
				Statement firstStatement = dataConnection.createStatement();
				ResultSet answer = firstStatement.executeQuery(query);
				while(answer.next())
				{
					results += answer.getString(1) + "\n";
				}
				answer.close();
				firstStatement.close();
				
		}
		catch (SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));
		return results;
	}
	
	/*
	 * A sample of the insert
	 * @return
	 */
	
	public int insertSample()
	{
		int rowsAffected = 0;
		String insertQuery = "INSERT INTO ''.'' () VALUES();";
		
		try
		{
			Statement insertStatement = dataConnection.createStatement();
			rowsAffected = insertStatement.executeUpdate(insertQuery);
			insertStatement.close();
			
		}
		catch(SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
			
		}
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));
		return rowsAffected;
	}
	
	/**
	 * Checks if there is a structure violation
	 * 
	 * @return if there is a structure violation
	 */
	private boolean checkForStructureViolation()
	{
		if (currentQuery.toUpperCase().contains(" DATABASE "))
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	/**
	 * Method for dropping tables/indices from a database. Throws exception if
	 * attempted
	 * 
	 * @throws SQLException
	 *             when the query contains a DROP DATABASE command.
	 */
	public void dropStatement()
	{
		String results = "";
		try
		{
			if (checkForStructureViolation())
			{
				throw new SQLException("You dropped the database.", "Try again.", Integer.MIN_VALUE);
			}

			if (currentQuery.toUpperCase().contains("INDEX"))
			{
				results = "The index was: ";
			}
			else
			{
				results = "The table was: ";
			}

			Statement dropStatement = dataConnection.createStatement();
			int affected = dropStatement.executeUpdate(currentQuery);

			dropStatement.close();

			if (affected == 0)
			{
				results += "dropped";
			}

			JOptionPane.showMessageDialog(baseController.getAppFrame(), results);
		}
		catch (SQLException dropError)
		{
			displayErrors(dropError);
		}
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));

	}
	
	public String[] getDatabaseColumnNames(String tableName)
	{
		String[] columns;
		currentQuery = "SELECT * FROM `" + tableName + "`";
		long startTime, endTime;
		startTime = System.currentTimeMillis();

		try
		{
			Statement firstStatement = dataConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(currentQuery);
			ResultSetMetaData answerData = answers.getMetaData();

			columns = new String[answerData.getColumnCount()];

			for (int column = 0; column < answerData.getColumnCount(); column++)
			{
				columns[column] = answerData.getColumnName(column + 1);
			}

			answers.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();

		}
		catch (SQLException currentException)
		{
			endTime = System.currentTimeMillis();
			columns = new String[] { "empty" };
			displayErrors(currentException);
		}

		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));
		return columns;
	}
	public ArrayList<QueryInfo> getQueryList()
	{
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));
		return getQueryList();
	}
	

	public String[] getMetaData()
	{
		String[] columnInformation;

		try
		{
			Statement firstStatement = dataConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(currentQuery);
			ResultSetMetaData myMeta = answer.getMetaData();

			columnInformation = new String[myMeta.getColumnCount()];

			for (int index = 0; index < myMeta.getColumnCount(); index++)
			{
				columnInformation[index] = myMeta.getColumnName(index + 1);
			}

			answer.close();
			firstStatement.close();

		}
		catch (SQLException currentException)
		{
			columnInformation = new String[] { "nada exists" };
			displayErrors(currentException);
		}
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));
		return columnInformation;
	}

	public String[][] realInfo()
	{
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));
		return null;
	}

	public String[][] tableInfo()
	{
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));
		return null;
	}
	
	/*
	 * Describes tables
	 */
	public String describeTable()
	{
		String results = "";
		String query = "DESCRIBE kwikee_marts";
		
		try
		{
				Statement firstStatement = dataConnection.createStatement();
				ResultSet answer = firstStatement.executeQuery(query);
				while(answer.next())
				{
					results += answer.getString(1) + "\t" + answer.getString(2) + "\t" + answer.getString(3) + "\t" + answer.getString(4) + "\n";
				}
				answer.close();
				firstStatement.close();
				
		}
		catch (SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));
		
		return results;
	}
	
	public DataFrame getAppFrame()
	{
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));
		return getAppFrame();
	}
	

	
	public void choosePivot()
	{
		
	}
	
}
